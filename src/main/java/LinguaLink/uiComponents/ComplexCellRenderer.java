package LinguaLink.uiComponents;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;

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
            wordLabel.setForeground(getForegroundColor(word));
            posLabel.setForeground(getMidgroundColor(word));
        } else {
            Color backgroundColor = getBackgroundColor(word);
            panel.setBackground(backgroundColor);
            textSplitPane.setBackground(backgroundColor);
            wordLabel.setForeground(getForegroundColor(word));
            posLabel.setForeground(getMidgroundColor(word));
        }

        panel.setPreferredSize(new Dimension(list.getWidth(), 50));

        return panel;
    }

    private Color getBackgroundColor(Word word) {
        // TODO: Refactor to get rid of this switch statement.
        return switch (word.getPartOfSpeech()) {
            case NOUN -> new Color(0, 95, 115);
            case VERB -> new Color(10, 147, 150);
            case PRONOUN -> new Color(148, 210, 189);
            case ARTICLE -> new Color(233, 216, 166);
            case ADVERB -> new Color(238, 155, 0);
            case ADJECTIVE -> new Color(202, 103, 2);
            case CONJUNCTION -> new Color(187, 62, 3);
            case PREPOSITION -> new Color(174, 32, 18);
            case INTERJECTION -> Color.getHSBColor(155, 34, 38);
        };
    }

    private Color getForegroundColor(Word word) {
        ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
        lightColors.add(PartOfSpeech.PRONOUN);
        lightColors.add(PartOfSpeech.ARTICLE);
        if (lightColors.contains(word.getPartOfSpeech())) { return Color.BLACK; }
        return Color.WHITE;
    }

    private Color getMidgroundColor(Word word) {
        ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
        lightColors.add(PartOfSpeech.PRONOUN);
        lightColors.add(PartOfSpeech.ARTICLE);
        if (lightColors.contains(word.getPartOfSpeech())) { return Color.DARK_GRAY; }
        return Color.LIGHT_GRAY;
    }

}
