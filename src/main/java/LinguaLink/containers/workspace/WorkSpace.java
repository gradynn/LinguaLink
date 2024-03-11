package LinguaLink.containers.workspace;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.wordblock.WordBlock;

import java.util.ArrayList;

public class WorkSpace {
    private static WorkSpace workSpace = null;
    private ArrayList<WordBlock> activeWords;
    private ArrayList<Connection> connections;

    private WorkSpace() {
        activeWords = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public static WorkSpace getInstance() {
        if (workSpace == null) {
            workSpace = new WorkSpace();
        }
        return workSpace;
    }

    public Iterable<WordBlock> getActiveWords() {
        return activeWords;
    }

    public Iterable<Connection> getConnections() {
        return connections;
    }
}
