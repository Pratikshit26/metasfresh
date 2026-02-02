import { describe, it, expect, vi, beforeEach } from 'vitest'

interface SubscriptionLimits {
  forms: number
  responsesPerMonth: number
  teamMembers: number
  storage: number // in MB
  customDomain: boolean
  removeWatermark: boolean
  advancedAnalytics: boolean
  apiAccess: boolean
}

interface Subscription {
  id: string
  userId: string
  tier: 'free' | 'pro' | 'enterprise'
  status: 'active' | 'cancelled' | 'past_due' | 'trialing'
  limits: SubscriptionLimits
  billingCycle: 'monthly' | 'yearly'
  currentPeriodStart: Date
  currentPeriodEnd: Date
  cancelAtPeriodEnd: boolean
}

class SubscriptionManager {
  private subscriptions: Map<string, Subscription> = new Map()

  getTierLimits(tier: 'free' | 'pro' | 'enterprise'): SubscriptionLimits {
    const limits = {
      free: {
        forms: 3,
        responsesPerMonth: 100,
        teamMembers: 1,
        storage: 100,
        customDomain: false,
        removeWatermark: false,
        advancedAnalytics: false,
        apiAccess: false
      },
      pro: {
        forms: 50,
        responsesPerMonth: 10000,
        teamMembers: 5,
        storage: 5000,
        customDomain: true,
        removeWatermark: true,
        advancedAnalytics: true,
        apiAccess: true
      },
      enterprise: {
        forms: -1, // unlimited
        responsesPerMonth: -1, // unlimited
        teamMembers: -1, // unlimited
        storage: -1, // unlimited
        customDomain: true,
        removeWatermark: true,
        advancedAnalytics: true,
        apiAccess: true
      }
    }

    return limits[tier]
  }

  createSubscription(userId: string, tier: 'free' | 'pro' | 'enterprise', billingCycle: 'monthly' | 'yearly'): Subscription {
    const now = new Date()
    const periodEnd = new Date(now)
    
    if (billingCycle === 'monthly') {
      periodEnd.setMonth(periodEnd.getMonth() + 1)
    } else {
      periodEnd.setFullYear(periodEnd.getFullYear() + 1)
    }

    const subscription: Subscription = {
      id: `sub_${Date.now()}`,
      userId,
      tier,
      status: tier === 'free' ? 'active' : 'trialing',
      limits: this.getTierLimits(tier),
      billingCycle,
      currentPeriodStart: now,
      currentPeriodEnd: periodEnd,
      cancelAtPeriodEnd: false
    }

    this.subscriptions.set(subscription.id, subscription)
    return subscription
  }

  getSubscription(subscriptionId: string): Subscription | undefined {
    return this.subscriptions.get(subscriptionId)
  }

  upgradeSubscription(subscriptionId: string, newTier: 'pro' | 'enterprise'): Subscription {
    const subscription = this.subscriptions.get(subscriptionId)
    if (!subscription) {
      throw new Error('Subscription not found')
    }

    subscription.tier = newTier
    subscription.limits = this.getTierLimits(newTier)
    subscription.status = 'active'

    return subscription
  }

  downgradeSubscription(subscriptionId: string, newTier: 'free' | 'pro'): Subscription {
    const subscription = this.subscriptions.get(subscriptionId)
    if (!subscription) {
      throw new Error('Subscription not found')
    }

    subscription.tier = newTier
    subscription.limits = this.getTierLimits(newTier)
    subscription.cancelAtPeriodEnd = true

    return subscription
  }

  cancelSubscription(subscriptionId: string, immediate: boolean = false): Subscription {
    const subscription = this.subscriptions.get(subscriptionId)
    if (!subscription) {
      throw new Error('Subscription not found')
    }

    if (immediate) {
      subscription.status = 'cancelled'
      subscription.tier = 'free'
      subscription.limits = this.getTierLimits('free')
    } else {
      subscription.cancelAtPeriodEnd = true
    }

    return subscription
  }

  checkLimit(userId: string, limitType: keyof SubscriptionLimits, currentUsage: number): boolean {
    // Find subscription for user
    const subscription = Array.from(this.subscriptions.values())
      .find(sub => sub.userId === userId && sub.status === 'active')

    if (!subscription) {
      return false
    }

    const limit = subscription.limits[limitType]
    
    // -1 means unlimited
    if (limit === -1) {
      return true
    }

    if (typeof limit === 'number') {
      return currentUsage < limit
    }

    return limit as boolean
  }

  reactivateSubscription(subscriptionId: string): Subscription {
    const subscription = this.subscriptions.get(subscriptionId)
    if (!subscription) {
      throw new Error('Subscription not found')
    }

    subscription.status = 'active'
    subscription.cancelAtPeriodEnd = false

    return subscription
  }

  handlePaymentFailed(subscriptionId: string): Subscription {
    const subscription = this.subscriptions.get(subscriptionId)
    if (!subscription) {
      throw new Error('Subscription not found')
    }

    subscription.status = 'past_due'
    return subscription
  }
}

describe('Subscription Manager', () => {
  let manager: SubscriptionManager

  beforeEach(() => {
    manager = new SubscriptionManager()
  })

  describe('Tier Limits', () => {
    it('should return correct limits for free tier', () => {
      const limits = manager.getTierLimits('free')

      expect(limits.forms).toBe(3)
      expect(limits.responsesPerMonth).toBe(100)
      expect(limits.teamMembers).toBe(1)
      expect(limits.customDomain).toBe(false)
      expect(limits.apiAccess).toBe(false)
    })

    it('should return correct limits for pro tier', () => {
      const limits = manager.getTierLimits('pro')

      expect(limits.forms).toBe(50)
      expect(limits.responsesPerMonth).toBe(10000)
      expect(limits.teamMembers).toBe(5)
      expect(limits.customDomain).toBe(true)
      expect(limits.advancedAnalytics).toBe(true)
    })

    it('should return unlimited limits for enterprise tier', () => {
      const limits = manager.getTierLimits('enterprise')

      expect(limits.forms).toBe(-1)
      expect(limits.responsesPerMonth).toBe(-1)
      expect(limits.teamMembers).toBe(-1)
      expect(limits.storage).toBe(-1)
    })
  })

  describe('Subscription Creation', () => {
    it('should create free subscription', () => {
      const subscription = manager.createSubscription('user-123', 'free', 'monthly')

      expect(subscription.userId).toBe('user-123')
      expect(subscription.tier).toBe('free')
      expect(subscription.status).toBe('active')
      expect(subscription.billingCycle).toBe('monthly')
    })

    it('should create pro subscription with trial status', () => {
      const subscription = manager.createSubscription('user-456', 'pro', 'monthly')

      expect(subscription.tier).toBe('pro')
      expect(subscription.status).toBe('trialing')
    })

    it('should set correct period end for monthly subscription', () => {
      const subscription = manager.createSubscription('user-789', 'pro', 'monthly')

      const expectedEnd = new Date()
      expectedEnd.setMonth(expectedEnd.getMonth() + 1)

      expect(subscription.currentPeriodEnd.getMonth()).toBe(expectedEnd.getMonth())
    })

    it('should set correct period end for yearly subscription', () => {
      const subscription = manager.createSubscription('user-999', 'pro', 'yearly')

      const expectedEnd = new Date()
      expectedEnd.setFullYear(expectedEnd.getFullYear() + 1)

      expect(subscription.currentPeriodEnd.getFullYear()).toBe(expectedEnd.getFullYear())
    })
  })

  describe('Subscription Retrieval', () => {
    it('should retrieve existing subscription', () => {
      const created = manager.createSubscription('user-111', 'pro', 'monthly')
      const retrieved = manager.getSubscription(created.id)

      expect(retrieved).toEqual(created)
    })

    it('should return undefined for non-existent subscription', () => {
      const subscription = manager.getSubscription('non-existent')

      expect(subscription).toBeUndefined()
    })
  })

  describe('Subscription Upgrades', () => {
    it('should upgrade from free to pro', () => {
      const subscription = manager.createSubscription('user-222', 'free', 'monthly')
      const upgraded = manager.upgradeSubscription(subscription.id, 'pro')

      expect(upgraded.tier).toBe('pro')
      expect(upgraded.limits.forms).toBe(50)
      expect(upgraded.status).toBe('active')
    })

    it('should upgrade from pro to enterprise', () => {
      const subscription = manager.createSubscription('user-333', 'pro', 'monthly')
      const upgraded = manager.upgradeSubscription(subscription.id, 'enterprise')

      expect(upgraded.tier).toBe('enterprise')
      expect(upgraded.limits.forms).toBe(-1)
    })

    it('should throw error when upgrading non-existent subscription', () => {
      expect(() => {
        manager.upgradeSubscription('non-existent', 'pro')
      }).toThrow('Subscription not found')
    })
  })

  describe('Subscription Downgrades', () => {
    it('should downgrade from pro to free', () => {
      const subscription = manager.createSubscription('user-444', 'pro', 'monthly')
      const downgraded = manager.downgradeSubscription(subscription.id, 'free')

      expect(downgraded.tier).toBe('free')
      expect(downgraded.cancelAtPeriodEnd).toBe(true)
    })

    it('should downgrade from enterprise to pro', () => {
      const subscription = manager.createSubscription('user-555', 'enterprise', 'monthly')
      const downgraded = manager.downgradeSubscription(subscription.id, 'pro')

      expect(downgraded.tier).toBe('pro')
      expect(downgraded.limits.forms).toBe(50)
    })
  })

  describe('Subscription Cancellation', () => {
    it('should cancel subscription at period end', () => {
      const subscription = manager.createSubscription('user-666', 'pro', 'monthly')
      const cancelled = manager.cancelSubscription(subscription.id, false)

      expect(cancelled.cancelAtPeriodEnd).toBe(true)
      expect(cancelled.status).toBe('trialing') // Still active until period end
    })

    it('should cancel subscription immediately', () => {
      const subscription = manager.createSubscription('user-777', 'pro', 'monthly')
      const cancelled = manager.cancelSubscription(subscription.id, true)

      expect(cancelled.status).toBe('cancelled')
      expect(cancelled.tier).toBe('free')
    })

    it('should throw error when cancelling non-existent subscription', () => {
      expect(() => {
        manager.cancelSubscription('non-existent')
      }).toThrow('Subscription not found')
    })
  })

  describe('Limit Checking', () => {
    it('should allow usage within limits', () => {
      const subscription = manager.createSubscription('user-888', 'free', 'monthly')
      const canCreate = manager.checkLimit('user-888', 'forms', 2)

      expect(canCreate).toBe(true)
    })

    it('should deny usage exceeding limits', () => {
      const subscription = manager.createSubscription('user-999', 'free', 'monthly')
      const canCreate = manager.checkLimit('user-999', 'forms', 3)

      expect(canCreate).toBe(false)
    })

    it('should allow unlimited usage for enterprise', () => {
      const subscription = manager.createSubscription('user-enterprise', 'enterprise', 'yearly')
      const canCreate = manager.checkLimit('user-enterprise', 'forms', 999999)

      expect(canCreate).toBe(true)
    })

    it('should check feature access', () => {
      const subscription = manager.createSubscription('user-pro', 'pro', 'monthly')
      const hasAccess = manager.checkLimit('user-pro', 'customDomain', 0)

      expect(hasAccess).toBe(true)
    })
  })

  describe('Subscription Reactivation', () => {
    it('should reactivate cancelled subscription', () => {
      const subscription = manager.createSubscription('user-reactivate', 'pro', 'monthly')
      manager.cancelSubscription(subscription.id, false)
      const reactivated = manager.reactivateSubscription(subscription.id)

      expect(reactivated.status).toBe('active')
      expect(reactivated.cancelAtPeriodEnd).toBe(false)
    })

    it('should throw error when reactivating non-existent subscription', () => {
      expect(() => {
        manager.reactivateSubscription('non-existent')
      }).toThrow('Subscription not found')
    })
  })

  describe('Payment Failure Handling', () => {
    it('should mark subscription as past_due on payment failure', () => {
      const subscription = manager.createSubscription('user-payment-fail', 'pro', 'monthly')
      const failed = manager.handlePaymentFailed(subscription.id)

      expect(failed.status).toBe('past_due')
    })

    it('should throw error for non-existent subscription', () => {
      expect(() => {
        manager.handlePaymentFailed('non-existent')
      }).toThrow('Subscription not found')
    })
  })

  describe('Edge Cases', () => {
    it('should handle multiple subscriptions for different users', () => {
      const sub1 = manager.createSubscription('user-1', 'free', 'monthly')
      const sub2 = manager.createSubscription('user-2', 'pro', 'monthly')
      const sub3 = manager.createSubscription('user-3', 'enterprise', 'yearly')

      expect(manager.getSubscription(sub1.id)?.userId).toBe('user-1')
      expect(manager.getSubscription(sub2.id)?.userId).toBe('user-2')
      expect(manager.getSubscription(sub3.id)?.userId).toBe('user-3')
    })

    it('should maintain subscription state across operations', () => {
      const subscription = manager.createSubscription('user-state', 'pro', 'monthly')
      
      manager.cancelSubscription(subscription.id, false)
      expect(manager.getSubscription(subscription.id)?.cancelAtPeriodEnd).toBe(true)

      manager.reactivateSubscription(subscription.id)
      expect(manager.getSubscription(subscription.id)?.cancelAtPeriodEnd).toBe(false)
    })
  })
})
