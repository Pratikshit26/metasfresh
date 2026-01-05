# Day 1: Arrays & Data Structures
**Date:** January 5, 2026  
**Status:** 🟡 In Progress  
**Time Required:** 4-5 hours

---

## Morning Session (9:00 AM - 12:00 PM)

### 📚 Theory Review (30 minutes)

#### Arrays Fundamentals
- **Time Complexity:**
  - Access: O(1)
  - Search: O(n)
  - Insert: O(n)
  - Delete: O(n)

#### Key Patterns:
1. **Two Pointers** - Use two indices to traverse array
2. **Sliding Window** - Maintain a window of elements
3. **Prefix Sum** - Pre-calculate cumulative sums
4. **Kadane's Algorithm** - Maximum subarray sum

---

## 🎯 Practice Problems (Start Now!)

### Problem 1: Two Sum ⭐ EASY
**Time:** 15 minutes  
**Link:** https://leetcode.com/problems/two-sum/

**Problem:**
```
Given an array of integers nums and an integer target, 
return indices of the two numbers that add up to target.

Example:
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: nums[0] + nums[1] == 9
```

**Hints:**
- Use a HashMap to store seen numbers
- For each number, check if (target - current) exists in map

**Solution Approach:**
1. Brute Force: O(n²) - Try all pairs
2. Hash Map: O(n) - Store complements

**Expected Time:** 10-15 minutes

---

### Problem 2: Best Time to Buy and Sell Stock ⭐ EASY
**Time:** 20 minutes  
**Link:** https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

**Problem:**
```
You are given an array prices where prices[i] is the price of a stock on day i.
Find the maximum profit. You may only buy once and sell once.

Example:
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5
```

**Hints:**
- Keep track of minimum price seen so far
- Calculate profit at each step

**Key Insight:** One pass solution with O(n) time

---

### Problem 3: Contains Duplicate ⭐ EASY
**Time:** 10 minutes  
**Link:** https://leetcode.com/problems/contains-duplicate/

**Problem:**
```
Given an integer array nums, return true if any value appears at least twice.

Example:
Input: nums = [1,2,3,1]
Output: true
```

**Hints:**
- Use HashSet
- Or sort and check adjacent elements

---

### Problem 4: Product of Array Except Self ⭐⭐ MEDIUM
**Time:** 25 minutes  
**Link:** https://leetcode.com/problems/product-of-array-except-self/

**Problem:**
```
Given an integer array nums, return an array answer such that 
answer[i] is equal to the product of all elements except nums[i].
You must write an algorithm that runs in O(n) time WITHOUT division.

Example:
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
```

**Hints:**
- Use prefix and suffix products
- Left pass: Store product of all elements to the left
- Right pass: Multiply by product of all elements to the right

**This is a common interview question!**

---

### Problem 5: Maximum Subarray (Kadane's Algorithm) ⭐⭐ MEDIUM
**Time:** 25 minutes  
**Link:** https://leetcode.com/problems/maximum-subarray/

**Problem:**
```
Given an integer array nums, find the contiguous subarray with largest sum.

Example:
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6
```

**Hints:**
- Kadane's Algorithm: Keep track of current sum and max sum
- If current sum becomes negative, reset to 0

**Algorithm:**
```python
max_sum = nums[0]
current_sum = 0
for num in nums:
    current_sum = max(num, current_sum + num)
    max_sum = max(max_sum, current_sum)
```

---

### Problem 6: Container With Most Water ⭐⭐ MEDIUM
**Time:** 30 minutes  
**Link:** https://leetcode.com/problems/container-with-most-water/

**Problem:**
```
Given n non-negative integers representing heights of lines,
find two lines that form a container with the most water.

Example:
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
```

**Hints:**
- Two pointer approach: Start with widest container
- Move the pointer with smaller height inward
- Area = min(height[left], height[right]) * (right - left)

---

## ☕ Break (10 minutes)

---

## Afternoon Session (2:00 PM - 5:00 PM)

### Problem 7: 3Sum ⭐⭐ MEDIUM
**Time:** 35 minutes  
**Link:** https://leetcode.com/problems/3sum/

**Problem:**
```
Given an integer array nums, return all triplets that sum to zero.

Example:
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

**Hints:**
- Sort the array first
- Fix one number, use two pointers for the other two
- Skip duplicates to avoid duplicate triplets

---

### Problem 8: Merge Intervals ⭐⭐ MEDIUM
**Time:** 30 minutes  
**Link:** https://leetcode.com/problems/merge-intervals/

**Problem:**
```
Given an array of intervals, merge all overlapping intervals.

Example:
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
```

**Hints:**
- Sort intervals by start time
- Check if current interval overlaps with previous

---

### Problem 9: Two Sum II - Input Array Is Sorted ⭐ EASY
**Time:** 15 minutes  
**Link:** https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

**Problem:**
```
Given a sorted array, find two numbers that add up to target.
Use O(1) extra space.

Example:
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
```

**Hints:**
- Two pointers: start and end
- If sum is too small, move left pointer right
- If sum is too large, move right pointer left

---

### Problem 10: Trapping Rain Water ⭐⭐⭐ HARD
**Time:** 40 minutes  
**Link:** https://leetcode.com/problems/trapping-rain-water/

**Problem:**
```
Given n non-negative integers representing elevation map,
compute how much water can be trapped after raining.

Example:
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
```

**Hints:**
- Water trapped at position i = min(max_left, max_right) - height[i]
- Approach 1: Pre-compute max_left and max_right arrays
- Approach 2: Two pointers (optimal)

**This is a challenging problem! Don't worry if you need hints.**

---

## 📝 Strings & HashMaps Bonus (If Time Permits)

### Problem 11: Valid Anagram ⭐ EASY
**Time:** 10 minutes  
**Link:** https://leetcode.com/problems/valid-anagram/

**Problem:**
```
Given two strings s and t, return true if t is an anagram of s.

Example:
Input: s = "anagram", t = "nagaram"
Output: true
```

---

### Problem 12: Longest Substring Without Repeating Characters ⭐⭐ MEDIUM
**Time:** 25 minutes  
**Link:** https://leetcode.com/problems/longest-substring-without-repeating-characters/

**Problem:**
```
Find length of longest substring without repeating characters.

Example:
Input: s = "abcabcbb"
Output: 3
Explanation: "abc" is the longest substring
```

**Hints:**
- Sliding window with HashSet
- Expand window when no duplicate
- Shrink window when duplicate found

---

### Problem 13: Group Anagrams ⭐⭐ MEDIUM
**Time:** 20 minutes  
**Link:** https://leetcode.com/problems/group-anagrams/

**Problem:**
```
Given an array of strings, group anagrams together.

Example:
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Hints:**
- Use sorted string as key in HashMap
- All anagrams will have same sorted string

---

## 📊 Progress Tracker

### Completed Problems:
- [ ] Two Sum
- [ ] Best Time to Buy and Sell Stock
- [ ] Contains Duplicate
- [ ] Product of Array Except Self
- [ ] Maximum Subarray
- [ ] Container With Most Water
- [ ] 3Sum
- [ ] Merge Intervals
- [ ] Two Sum II
- [ ] Trapping Rain Water
- [ ] Valid Anagram (Bonus)
- [ ] Longest Substring (Bonus)
- [ ] Group Anagrams (Bonus)

**Target:** Complete at least 7-8 problems today

---

## 💡 Key Takeaways to Remember

### Two Pointers Pattern:
```
left = 0, right = len(arr) - 1
while left < right:
    # Process
    if condition:
        left += 1
    else:
        right -= 1
```

### Sliding Window Pattern:
```
left = 0
for right in range(len(arr)):
    # Expand window
    while invalid_window:
        # Shrink window
        left += 1
```

### HashMap for O(1) Lookup:
```
seen = {}
for i, num in enumerate(nums):
    complement = target - num
    if complement in seen:
        return [seen[complement], i]
    seen[num] = i
```

---

## 🎯 Evening Review (7:00 PM - 8:00 PM)

### Review Checklist:
- [ ] Revisit any problems you couldn't solve
- [ ] Write down patterns you learned
- [ ] Note which problem types were difficult
- [ ] Practice explaining solutions out loud
- [ ] Add notes on time/space complexity

### Questions to Ask Yourself:
1. Can I explain this solution to someone else?
2. What's the time and space complexity?
3. Are there alternative approaches?
4. What edge cases should I consider?

---

## 📈 Success Metrics for Today

- ✅ Completed 7+ problems
- ✅ Understood two-pointer technique
- ✅ Mastered HashMap usage for lookups
- ✅ Can explain Kadane's algorithm
- ✅ Comfortable with sliding window basics

---

## 🔥 Pro Tips

1. **Start a timer** - Simulate interview pressure
2. **Code without IDE help** - Use basic text editor
3. **Talk out loud** - Practice communication
4. **Draw examples** - Visualize the problem
5. **Test with edge cases** - Empty array, single element, duplicates

---

## Tomorrow's Preview

**Day 2: Linked Lists & Trees**
- Reverse linked list patterns
- Fast & slow pointer technique
- Tree traversals (DFS/BFS)
- Binary search tree operations

**Rest well tonight! 🌙**
