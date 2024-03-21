package LinguaLink.containers.workspace;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

public class WorkSpace {
    private static WorkSpace workSpace = null;
    private ArrayList<WordBlock> wordBlocks;
    private ArrayList<Connection> connections;

    /**
     * Private Class Constructor for Singleton pattern.
     */
    private WorkSpace() {
        wordBlocks = new ArrayList<>();
        connections = new ArrayList<>();
    }

    /**
     * Returns reference to Singleton WorkSpace.
     * @return WorkSpace object.
     */
    public static WorkSpace getInstance() {
        if (workSpace == null) {
            workSpace = new WorkSpace();
        }
        return workSpace;
    }

    /**
     * Clears WorkSpace of all words.
     */
    public void clearWorkSpace() {
        connections.clear();
        wordBlocks.clear();
    }

    /**
     * Gets list of all active WordBlocks in WorkSpace.
     * @return Unmodifiable list of WordBlock objects.
     */
    public List<WordBlock> getWordBlocks() {
        return Collections.unmodifiableList(wordBlocks);
    }

    /**
     * Gets a list of all active Connections in WorkSpace.
     * @return Unmodifiable list of Connection objects.
     */
    public List<Connection> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    /**
     * Adds new WordBlock to the WorkSpace.
     * @param word: Word object to add new WordBlock for.
     * @param x: int representing x value of new WordBlock.
     * @param y: int representing y value of new WordBlock.
     * @return reference to new WordBlock
     */
    public WordBlock addWord(Word word, int x, int y) {
        WordBlock newWordBlock = new WordBlock(new Point(x, y), word);
        wordBlocks.add(newWordBlock);
        return newWordBlock;
    }

    /**
     * Removes a given WordBlock from the canvas. Also deletes all connections in WorkSpace involving passed WordBlock.
     * @param wordBlock: WordBlock object to remove.
     */
    public void removeWordBlock(WordBlock wordBlock) {
        wordBlocks.remove(wordBlock);

        // Collect connections to remove
        List<Connection> toRemove = new ArrayList<>();
        for (Connection c : connections) {
            if (c.getFrom().equals(wordBlock) || c.getTo().equals(wordBlock)) {
                toRemove.add(c);
            }
        }

        // Remove the collected connections
        connections.removeAll(toRemove);
    }

    /**
     * Removes a connection from the WorkSpace.
     * @param toDelete: Connection object to be removed.
     */
    public void removeConnection(Connection toDelete) {
        connections.remove(toDelete);
    }

    /**
     * Adds a new connection to the WorkSpace.
     * @param toAdd: Connection object to be added.
     */
    public void addConnection(Connection toAdd) {
        connections.add(toAdd);
    }
}
