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


    private WorkSpace() {
        wordBlocks = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public static WorkSpace getInstance() {
        if (workSpace == null) {
            workSpace = new WorkSpace();
        }
        return workSpace;
    }

    public void clearWorkSpace() {
        connections.clear();
        wordBlocks.clear();
    }

    public List<WordBlock> getWordBlocks() {
        return Collections.unmodifiableList(wordBlocks);
    }

    public List<Connection> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    public WordBlock addWord(Word word, int x, int y) {
        WordBlock newWordBlock = new WordBlock(new Point(x, y), word);
        wordBlocks.add(newWordBlock);
        System.out.println("Words now in the models representation of WorkSpace:");
        for (WordBlock w : wordBlocks) {
            System.out.println(w.getWord().getWord());
        }
        return newWordBlock;
    }

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

    public void removeConnection(Connection toDelete) {
        connections.remove(toDelete);
    }

    public void addConnection(Connection toAdd) {
        connections.add(toAdd);
    }
}
