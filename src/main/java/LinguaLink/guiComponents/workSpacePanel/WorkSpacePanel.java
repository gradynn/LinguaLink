package LinguaLink.guiComponents.workSpacePanel;

import LinguaLink.Controller;
import LinguaLink.Util;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class WorkSpacePanel extends JPanel {
	private Map<WordBlock, Shape> wordBlockShapes = new HashMap<>();
	private WordBlock draggedWordBlock;
	private Point lastDragPoint;
	private Controller controller;

	public WorkSpacePanel() {
		controller = Controller.getInstance();
		setBackground(Color.WHITE);
		setupMouseHandling();
	}

	private void setupMouseHandling() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
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

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
						if (entry.getValue().contains(e.getPoint())) {
							WordBlock wordBlock = entry.getKey();
							controller.moveWordToWordBank(wordBlock);
							break;
						}
					}
				}
			}
		};

		addMouseListener(mouseAdapter);
		addMouseMotionListener(new MouseMotionAdapter() {
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
		});
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
			WordBlock wordBlock = entry.getKey();
			Shape shape = entry.getValue();
			g2.setColor(Util.getBackgroundColor(wordBlock.getWord().getPartOfSpeech()));
			g2.fill(shape);

			g2.setColor(Util.getPrimaryTextColor(wordBlock.getWord().getPartOfSpeech())); // Text color
			g2.drawString(wordBlock.getWord().getWord(), (float) shape.getBounds().getX() + 5, (float) shape.getBounds().getY() + 20);
			g2.drawString(wordBlock.getWord().getPartOfSpeech().toString(), (float) shape.getBounds().getX() + 5, (float) shape.getBounds().getY() + 40);
		}
	}
}