package LinguaLink.uiComponents.workSpacePanel;

import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class WorkSpacePanel extends JPanel {
	private Map<WordBlock, Rectangle> wordBlockRectangles = new HashMap<>();
	private WordBlock draggedWordBlock;
	private Point lastDragPoint;
	private Controller controller;

	public WorkSpacePanel() {
		controller = Controller.getInstance();

		setBackground(Color.WHITE); // Set a background color different from black to see the words
		setupMouseHandling();
	}

	private void setupMouseHandling() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (Map.Entry<WordBlock, Rectangle> entry : wordBlockRectangles.entrySet()) {
					if (entry.getValue().contains(e.getPoint())) {
						draggedWordBlock = entry.getKey();
						lastDragPoint = e.getPoint();
						break;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				draggedWordBlock = null;
				lastDragPoint = null;
			}
		};

		MouseMotionAdapter motionAdapter = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (draggedWordBlock != null && lastDragPoint != null) {
					int dx = e.getX() - lastDragPoint.x;
					int dy = e.getY() - lastDragPoint.y;
					Rectangle rect = wordBlockRectangles.get(draggedWordBlock);
					rect.translate(dx, dy);
					lastDragPoint = e.getPoint();

					controller.setWordBlockPosition(draggedWordBlock, dx, dy);

					repaint();
				}
			}
		};

		addMouseListener(mouseAdapter);
		addMouseMotionListener(motionAdapter);
	}

	public void addWordBlock(WordBlock wordBlock) {
		// Assuming WordBlock has a method to get its position
		wordBlockRectangles.put(wordBlock, new Rectangle(wordBlock.getPosition(), new Dimension(100, 50)));
	}

	public void clearWordBlocks() {
		wordBlockRectangles.clear();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Map.Entry<WordBlock, Rectangle> entry : wordBlockRectangles.entrySet()) {
			WordBlock wordBlock = entry.getKey();
			Rectangle rect = entry.getValue();
			g.setColor(getColorForWordBlock(wordBlock));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	private Color getColorForWordBlock(WordBlock wordBlock) {
		// This method would return a color based on the WordBlock properties, for example:
		return switch (wordBlock.getWord().getPartOfSpeech()) {
			case NOUN -> Color.BLUE;
			case VERB -> Color.RED;
			// Add cases for other parts of speech
			default -> Color.GRAY;
		};
	}
}
