package LinguaLink.guiComponents;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComplexCellRenderer implements ListCellRenderer<Word> {
    @Override
    public Component getListCellRendererComponent(
            JList<? extends Word> list, Word word, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel wordLabel = new JLabel(word.getWord());
        JLabel posLabel = new JLabel(word.getPartOfSpeech().toString());

        JSplitPane textSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, wordLabel, posLabel);
        textSplitPane.setDividerSize(0); // Remove the visible divider
        textSplitPane.setBorder(null); // Remove border from split pane to avoid extra space
        textSplitPane.setResizeWeight(1.0); // Give all extra space to the top component
        textSplitPane.setEnabled(false); // Disable resizing of split pane
        textSplitPane.setOpaque(false);

        panel.add(textSplitPane, BorderLayout.WEST);

        if (isSelected) {
            panel.setBackground(list.getSelectionBackground());
            textSplitPane.setBackground(list.getSelectionBackground());
            wordLabel.setForeground(Util.getPrimaryTextColor(word.getPartOfSpeech()));
            posLabel.setForeground(Util.getSecondaryTextColor(word.getPartOfSpeech()));
        } else {
            Color backgroundColor = Util.getBackgroundColor(word.getPartOfSpeech());
            panel.setBackground(backgroundColor);
            textSplitPane.setBackground(backgroundColor);
            wordLabel.setForeground(Util.getPrimaryTextColor(word.getPartOfSpeech()));
            posLabel.setForeground(Util.getSecondaryTextColor(word.getPartOfSpeech()));
        }

        panel.setPreferredSize(new Dimension(list.getWidth(), 50));

        return panel;
    }
}
