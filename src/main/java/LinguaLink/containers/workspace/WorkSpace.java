package LinguaLink.containers.workspace;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

public class WorkSpace {
    private static WorkSpace workSpace = null;
    private Map<WordBlock, ArrayList<Connection>> graph;

    private WorkSpace() {
        graph = new HashMap<>();
    }

    public static WorkSpace getInstance() {
        if (workSpace == null) {
            workSpace = new WorkSpace();
        }
        return workSpace;
    }

    public void clearWorkSpace() {
        graph.clear();
    }

    public ArrayList<WordBlock> getWordBlocks() {
        return new ArrayList<>(graph.keySet());
    }

    public ArrayList<Connection> getConnectionsFor(WordBlock wordBlock) {
        return graph.getOrDefault(wordBlock, new ArrayList<>());
    }

    public boolean isValidW() {
        for (ArrayList<Connection> connections : graph.values()) {
            for (Connection c : connections) {
                if (!c.isValid()) { return false; }
            }
        }
        return true;
    }

    public void addWord(Word word) {
        WordBlock newWordBlock = new WordBlock(new Point(0, 0), word);
        graph.put(newWordBlock, new ArrayList<Connection>());
    }
}
