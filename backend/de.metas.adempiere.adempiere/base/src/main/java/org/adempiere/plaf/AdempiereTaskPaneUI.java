package org.adempiere.plaf;

 

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.ComponentUI;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.plaf.windows.WindowsTaskPaneUI;

/**
 * {@link JXTaskPane} UI
 * 
 * NOTE: don't use it directly. It is registered via {@link AdempiereLookAndFeel}.
 * 
 * @author tsa
 *
 */
public class AdempiereTaskPaneUI extends WindowsTaskPaneUI
{
	/** UI SubClass ID for Standard Window-Tab's collapsible fields group panel */
	public static final String UISUBCLASSID_VPanel_FieldGroup = "VPanel.FieldGroup";
	/** UI SubClass ID for Standard Window-Tab's collapsible search panel */
	public static final String UISUBCLASSID_VPanel_FindPanel = "VPanel.FindPanel";

	public static final String KEY_TaskPane_InsideBorder = "TaskPane.InsideBorder";

	public static ComponentUI createUI(final JComponent c)
	{
		final UISubClassIDHelper uiSubClassHelper = UISubClassIDHelper.ofComponent(c);
		return new AdempiereTaskPaneUI(uiSubClassHelper);
	}

	private final UISubClassIDHelper uiSubClassHelper;

	private AdempiereTaskPaneUI(final UISubClassIDHelper uiSubClassHelper)
	{
		super();
		this.uiSubClassHelper = uiSubClassHelper;
	}

	@Override
	protected void installDefaults()
	{
		super.installDefaults();

		final boolean animated = uiSubClassHelper.getBoolean("TaskPane.animate", false);
		group.setAnimated(animated);
	}

	@Override
	protected AdempierePaneBorder createPaneBorder()
	{
		return new AdempierePaneBorder();
	}

	@Override
	protected Border createContentPaneBorder()
	{
		final Color borderColor = uiSubClassHelper.getColor("TaskPane.borderColor");
		final Border insideBorder = uiSubClassHelper.getBorder(KEY_TaskPane_InsideBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10));

		return new CompoundBorder(new ContentPaneBorder(borderColor), insideBorder);
	}

	@Override
	protected void configure(JXHyperlink link)
	{
		super.configure(link);
		link.setForeground(uiSubClassHelper.getColor("TaskPane.titleForeground", link.getForeground()));
		link.setFocusable(false);
	}

	class AdempierePaneBorder extends PaneBorder
	{
		public AdempierePaneBorder()
		{
			super();

			borderColor = uiSubClassHelper.getColor("TaskPane.borderColor", borderColor);

			titleForeground = uiSubClassHelper.getColor("TaskPane.titleForeground", titleForeground);

			specialTitleBackground = uiSubClassHelper.getColor("TaskPane.specialTitleBackground", specialTitleBackground);
			specialTitleForeground = uiSubClassHelper.getColor("TaskPane.specialTitleForeground", specialTitleForeground);

			titleBackgroundGradientStart = uiSubClassHelper.getColor("TaskPane.titleBackgroundGradientStart", titleBackgroundGradientStart);
			titleBackgroundGradientEnd = uiSubClassHelper.getColor("TaskPane.titleBackgroundGradientEnd", titleBackgroundGradientEnd);

			titleOver = uiSubClassHelper.getColor("TaskPane.titleOver", titleOver);
			specialTitleOver = uiSubClassHelper.getColor("TaskPane.specialTitleOver", specialTitleOver);
		}

		@Override
		protected void paintTitleBackground(JXTaskPane group, Graphics g)
		{
			if (group.isSpecial())
			{
				g.setColor(specialTitleBackground);
				g.fillRoundRect(
						0,
						0,
						group.getWidth(),
						getRoundHeight() * 2,
						getRoundHeight(),
						getRoundHeight());
				g.fillRect(
						0,
						getRoundHeight(),
						group.getWidth(),
						getTitleHeight(group) - getRoundHeight());
			}
			else
			{
				Paint oldPaint = ((Graphics2D)g).getPaint();
				GradientPaint gradient = new GradientPaint(
						0f,
						group.getWidth() / 2,
						group.getComponentOrientation().isLeftToRight() ? titleBackgroundGradientStart : titleBackgroundGradientEnd,
						group.getWidth(),
						getTitleHeight(group),
						group.getComponentOrientation().isLeftToRight() ? titleBackgroundGradientEnd : titleBackgroundGradientStart);

				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				((Graphics2D)g).setPaint(gradient);
				g.fillRoundRect(
						0,
						0,
						group.getWidth(),
						getRoundHeight() * 2,
						getRoundHeight(),
						getRoundHeight());
				g.fillRect(
						0,
						getRoundHeight(),
						group.getWidth(),
						getTitleHeight(group) - getRoundHeight());
				((Graphics2D)g).setPaint(oldPaint);
			}
		}

		@Override
		protected void paintExpandedControls(JXTaskPane group, Graphics g, int x, int y, int width, int height)
		{
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			paintOvalAroundControls(group, g, x, y, width, height);
			g.setColor(getPaintColor(group));
			paintChevronControls(group, g, x, y, width, height);

			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}

		@Override
		protected boolean isMouseOverBorder()
		{
			return true;
		}

	}
}
