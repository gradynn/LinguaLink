package LinguaLink.guiComponents.workSpacePanel;

import LinguaLink.Controller;
import LinguaLink.Model;
import LinguaLink.Util;
import LinguaLink.components.connection.Connection;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkSpacePanel extends JPanel {
	private Map<WordBlock, Shape> wordBlockShapes = new HashMap<>();
	private WordBlock draggedWordBlock;
	private Point lastDragPoint;
	private Controller controller;
	private Model model;
	private WordBlock selectedWordBlock;
	private WordBlock firstSelectedBlock;

	public WorkSpacePanel() {
		controller = Controller.getInstance();
		model = Model.getInstance();
		setBackground(Color.WHITE);
		setFocusable(true);
		setupMouseHandling();
		createPopupMenu();
	}

	private void setupMouseHandling() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
				WordBlock clickedBlock = null;
				boolean clickedOnBlock = false;
				for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
					if (entry.getValue().contains(e.getPoint())) {
						clickedBlock = entry.getKey();
						clickedOnBlock = true;
						lastDragPoint = e.getPoint();  // Set initial drag point

						// Select or drag the block
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (e.isShiftDown() && firstSelectedBlock != null && clickedBlock != firstSelectedBlock) {
								controller.addConnection(new Connection(firstSelectedBlock, clickedBlock));
								firstSelectedBlock = null; // Reset for the next connection
							} else {
								firstSelectedBlock = clickedBlock;
								draggedWordBlock = clickedBlock;
								selectedWordBlock = clickedBlock; // For highlighting or other actions
							}
						}

						// Show the popup menu on right click
						if (SwingUtilities.isRightMouseButton(e)) {
							createPopupMenu().show(WorkSpacePanel.this, e.getX(), e.getY());
						}

						break;
					}
				}

				if (!clickedOnBlock) {
					selectedWordBlock = null;
					firstSelectedBlock = null;
					draggedWordBlock = null;
				}

				repaint();  // Repaint to update the workspace
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				draggedWordBlock = null;
				lastDragPoint = null;
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (draggedWordBlock != null && lastDragPoint != null) {
					int dx = e.getX() - lastDragPoint.x;
					int dy = e.getY() - lastDragPoint.y;
					Shape shape = wordBlockShapes.get(draggedWordBlock);
					if (shape instanceof RoundRectangle2D) {
						RoundRectangle2D rect = (RoundRectangle2D) shape;
						rect.setFrame(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());
					}
					lastDragPoint = e.getPoint();

					controller.setWordBlockPosition(draggedWordBlock, dx, dy);

					repaint();
				}
			}
		};

		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter); // Use the same adapter for motion events
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem moveToBankItem = new JMenuItem("Move to Word Bank");
		moveToBankItem.addActionListener(e -> {
			if (selectedWordBlock != null) {
				controller.moveWordToWordBank(selectedWordBlock);
				wordBlockShapes.remove(selectedWordBlock);
				repaint();
			}
		});

		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(e -> {
			if (selectedWordBlock != null) {
				safeDeleteWordBlock(selectedWordBlock);
			}
		});

		popupMenu.add(moveToBankItem);
		popupMenu.add(deleteItem);

		return popupMenu;
	}
	public void addWordBlock(WordBlock wordBlock) {
		RoundRectangle2D roundRect = new RoundRectangle2D.Double(
				wordBlock.getPosition().x, wordBlock.getPosition().y, 100, 50, 10, 10);
		wordBlockShapes.put(wordBlock, roundRect);
	}

	public void clearWordBlocks() {
		wordBlockShapes.clear();
		repaint();
	}

	protected void safeDeleteWordBlock(WordBlock toDelete) {
		wordBlockShapes.remove(selectedWordBlock);
		controller.deleteWordBlock(selectedWordBlock);
		selectedWordBlock = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Draw connections
		List<Connection> connectionList = model.getActiveConnections();
		g2.setColor(Color.BLACK);
		for (Connection conn : connectionList) {
			Point fromPoint = getCenterPoint(wordBlockShapes.get(conn.getFrom()));
			Point toPoint = getCenterPoint(wordBlockShapes.get(conn.getTo()));
			g2.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
		}

		for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
			WordBlock wordBlock = entry.getKey();
			Shape shape = entry.getValue();

			// Fill the shape with its background color
			g2.setColor(Util.getBackgroundColor(wordBlock.getWord().getPartOfSpeech()));
			g2.fill(shape);

			// Draw text inside the shape
			g2.setColor(Util.getPrimaryTextColor(wordBlock.getWord().getPartOfSpeech()));
			g2.drawString(wordBlock.getWord().getWord(), (float) shape.getBounds().getX() + 5, (float) shape.getBounds().getY() + 20);
			g2.drawString(wordBlock.getWord().getPartOfSpeech().toString(), (float) shape.getBounds().getX() + 5, (float) shape.getBounds().getY() + 40);

			// If this is the selected word block, draw a highlight border around it
			if (wordBlock == selectedWordBlock) {
				g2.setStroke(new BasicStroke(2)); // Set stroke width for the highlight
				g2.setColor(Color.BLUE); // Highlight color
				g2.draw(shape); // Draw the highlight around the shape
			}
		}
	}

	private Point getCenterPoint(Shape shape) {
		Rectangle2D bounds = shape.getBounds2D();
		int centerX = (int) bounds.getCenterX();
		int centerY = (int) bounds.getCenterY();
		return new Point(centerX, centerY);
	}

}