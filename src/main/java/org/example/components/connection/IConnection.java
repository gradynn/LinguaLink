package org.example.components.connection;

import org.example.components.wordblock.WordBlock;

public interface IConnection {
    public WordBlock getFrom();
    public WordBlock getTo();
    public boolean isValid();
}
