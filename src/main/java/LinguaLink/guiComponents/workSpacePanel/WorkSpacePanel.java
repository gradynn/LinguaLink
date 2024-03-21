package LinguaLink.guiComponents.workSpacePanel;

import LinguaLink.Controller;
import LinguaLink.Model;
import LinguaLink.Util;
import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.guiComponents.wordTransferable.WordTransferable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.geom.Line2D;

public class WorkSpacePanel extends JPanel {
	private Map<WordBlock, Shape> wordBlockShapes = new HashMap<>();
	private WordBlock draggedWordBlock;
	private Point lastDragPoint;
	private Controller controller;
	private Model model;
	private WordBlock selectedWordBlock;
	private Connection selectedConnection;
	private WordBlock firstSelectedBlock;

	/**
	 * Constructs a WorkSpacePanel and initializes its components and event handling.
	 */
	public WorkSpacePanel() {
		controller = Controller.getInstance();
		model = Model.getInstance();
		setBackground(Color.WHITE);
		setFocusable(true);
		setupMouseHandling();
		createPopupMenu();

		setDropTarget(new DropTarget(this, DnDConstants.ACTION_COPY, new DropTargetListener() {
			public void drop(DropTargetDropEvent dtde) {
				try {
					Transferable tr = dtde.getTransferable();
					if (tr.isDataFlavorSupported(WordTransferable.WORD_FLAVOR)) {
						Word word = (Word) tr.getTransferData(WordTransferable.WORD_FLAVOR);
						Point dropPoint = dtde.getLocation(); // Get drop location
						createWordBlockAtLocation(word, dropPoint);
						dtde.acceptDrop(DnDConstants.ACTION_COPY);
						dtde.dropComplete(true);
					} else {
						dtde.rejectDrop();
					}
				} catch (Exception ex) {
					dtde.rejectDrop();
				}
				repaint();
			}

			public void dragEnter(DropTargetDragEvent dtde) {}
			public void dragOver(DropTargetDragEvent dtde) {}
			public void dropActionChanged(DropTargetDragEvent dtde) {}
			public void dragExit(DropTargetEvent dte) {}
		}, true, null));
	}

	/**
	 * Creates a WordBlock at a specific location on the workspace.
	 * @param word     The word to be placed in the workspace.
	 * @param location The location where the word block should be created.
	 */
	private void createWordBlockAtLocation(Word word, Point location) {
		WordBlock createdWordBlock = controller.moveWordToWorkSpace(word, location.x, location.y);
		controller.setWordBlockPositionAbs(createdWordBlock, location.x, location.y);
	}

	/**
	 * Sets up mouse handling for the workspace panel, including clicking, dragging, and releasing actions.
	 */
	private void setupMouseHandling() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();

				Point clickPoint = e.getPoint();
				selectedConnection = null;  // Deselect previous connection

				// Check if clicked near a connection
				for (Connection conn : model.getActiveConnections()) {
					Point fromPoint = getCenterPoint(wordBlockShapes.get(conn.getFrom()));
					Point toPoint = getCenterPoint(wordBlockShapes.get(conn.getTo()));

					if (clickedNearLine(clickPoint, fromPoint, toPoint)) {
						selectedConnection = conn;
						break;
					}
				}

				if (selectedConnection != null && SwingUtilities.isRightMouseButton(e)) {
					createConnectionPopupMenu().show(WorkSpacePanel.this, e.getX(), e.getY());
					return;
				}

				WordBlock clickedBlock = null;
				boolean clickedOnBlock = false;
				for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
					if (entry.getValue().contains(e.getPoint())) {
						clickedBlock = entry.getKey();
						clickedOnBlock = true;
						lastDragPoint = e.getPoint(); // Set initial drag point

						if (SwingUtilities.isRightMouseButton(e)) {
							selectedWordBlock = clickedBlock; // Update the selected block on right-click
							createPopupMenu().show(WorkSpacePanel.this, e.getX(), e.getY());
						} else if (SwingUtilities.isLeftMouseButton(e)) {
							if (e.getClickCount() == 2) {
								controller.moveWordToWordBank(clickedBlock);
							} else if (e.isShiftDown() &&
											firstSelectedBlock != null &&
											clickedBlock != firstSelectedBlock) {
								controller.addConnection(new Connection(firstSelectedBlock, clickedBlock));
								firstSelectedBlock = null; // Reset for the next connection
							} else {
								firstSelectedBlock = clickedBlock;
								draggedWordBlock = clickedBlock;
								selectedWordBlock = clickedBlock; // For highlighting or other actions
							}
						}
						break;
					}
				}

				if (!clickedOnBlock && SwingUtilities.isLeftMouseButton(e)) {
					selectedWordBlock = null;
					firstSelectedBlock = null;
					draggedWordBlock = null;
				}

				repaint();
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

	/**
	 * Checks if a click event is near a line, used to detect click events near connections.
	 * @param click      The point where the mouse was clicked.
	 * @param lineStart  The starting point of the line.
	 * @param lineEnd    The ending point of the line.
	 * @return true if the click was near the line, false otherwise.
	 */
	private boolean clickedNearLine(Point click, Point lineStart, Point lineEnd) {
		// Simple way: check if click is within a small rectangle around the line
		// More complex: calculate distance from click to line segment
		final int NEARBY_DISTANCE = 5;  // Adjust based on how close to the line a click is considered
		return new Line2D.Float(lineStart, lineEnd).ptSegDist(click) <= NEARBY_DISTANCE;
	}

	/**
	 * Creates a popup menu for the workspace panel.
	 * @return A JPopupMenu object for the workspace.
	 */
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

	/**
	 * Creates a popup menu for managing connections in the workspace panel.
	 * @return A JPopupMenu object specific for connections.
	 */
	private JPopupMenu createConnectionPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem deleteConnectionItem = new JMenuItem("Delete Connection");
		deleteConnectionItem.addActionListener(e -> {
			if (selectedConnection != null) {
				controller.deleteConnection(selectedConnection);
				selectedConnection = null;  // Clear selection after deletion
				repaint();
			}
		});

		popupMenu.add(deleteConnectionItem);
		return popupMenu;
	}

	/**
	 * Adds a WordBlock to the workspace panel and updates its display.
	 * @param wordBlock The WordBlock to add.
	 */
	public void addWordBlock(WordBlock wordBlock) {
		RoundRectangle2D roundRect = new RoundRectangle2D.Double(
						wordBlock.getPosition().x, wordBlock.getPosition().y, 100, 50, 10, 10);
		wordBlockShapes.put(wordBlock, roundRect);
		repaint();
	}

	/**
	 * Adds a WordBlock to the workspace panel at a specific coordinate and updates its display.
	 * @param wordBlock The WordBlock to add.
	 * @param x         The x-coordinate where the block will be placed.
	 * @param y         The y-coordinate where the block will be placed.
	 */
	public void addWordBlock(WordBlock wordBlock, int x, int y) {
		RoundRectangle2D roundRect = new RoundRectangle2D.Double(
						x, y, 100, 50, 10, 10);
		wordBlockShapes.put(wordBlock, roundRect);
		repaint();
	}

	/**
	 * Clears all WordBlocks from the workspace panel.
	 */
	public void clearWordBlocks() {
		wordBlockShapes.clear();
		repaint();
	}

	/**
	 * Safely deletes a WordBlock from the workspace panel and updates its display.
	 * @param toDelete The WordBlock to be deleted.
	 */
	protected void safeDeleteWordBlock(WordBlock toDelete) {
		wordBlockShapes.remove(selectedWordBlock);
		controller.deleteWordBlock(selectedWordBlock);
		selectedWordBlock = null;
		repaint();
	}

	/**
	 * Paints the components of the workspace panel, including word blocks and connections.
	 * @param g The Graphics object to be used for painting.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Draw connections
		List<Connection> connectionList = model.getActiveConnections();
		for (Connection conn : connectionList) {
			Point fromPoint = getCenterPoint(wordBlockShapes.get(conn.getFrom()));
			Point toPoint = getCenterPoint(wordBlockShapes.get(conn.getTo()));

			if (conn == selectedConnection){
				g2.setStroke(new BasicStroke(10.0f));
				g2.setColor(Color.BLUE);
			} else if (conn.isValid()) {
				g2.setStroke(new BasicStroke(5.0f));
				g2.setColor(Color.BLACK);
			} else {
				g2.setStroke(new BasicStroke(10.0f));
				g2.setColor(Color.RED);
			}

			g2.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
		}

		// Reset to default stroke for other graphics operations
		g2.setStroke(new BasicStroke());

		for (Map.Entry<WordBlock, Shape> entry : wordBlockShapes.entrySet()) {
			WordBlock wordBlock = entry.getKey();
			Shape shape = entry.getValue();

			// Set color and fill shape for word blocks
			g2.setColor(Util.getBackgroundColor(wordBlock.getWord().getPartOfSpeech()));
			g2.fill(shape);

			// Draw text inside the shape
			g2.setColor(Util.getPrimaryTextColor(wordBlock.getWord().getPartOfSpeech()));
			g2.drawString(
							wordBlock.getWord().getWord(),
							(float) shape.getBounds().getX() + 5,
							(float) shape.getBounds().getY() + 20
			);
			g2.drawString(
							wordBlock.getWord().getPartOfSpeech().toString(),
							(float) shape.getBounds().getX() + 5,
							(float) shape.getBounds().getY() + 40
			);

			// Highlight if selected
			if (wordBlock == selectedWordBlock) {
				g2.setStroke(new BasicStroke(2)); // Set stroke width for highlight
				g2.setColor(Color.BLUE); // Set highlight color
				g2.draw(shape); // Draw highlight
				g2.setStroke(new BasicStroke()); // Reset stroke after highlight
			}
		}
	}

	/**
	 * Calculates the center point of a given shape, usually a word block's bounding box.
	 * @param shape The shape whose center point is to be calculated.
	 * @return The center point of the shape.
	 */
	private Point getCenterPoint(Shape shape) {
		Rectangle2D bounds = shape.getBounds2D();
		int centerX = (int) bounds.getCenterX();
		int centerY = (int) bounds.getCenterY();
		return new Point(centerX, centerY);
	}

}