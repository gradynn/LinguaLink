package LinguaLink.guiComponents.wordTransferable;

import LinguaLink.components.word.Word;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class WordTransferable implements Transferable {
	public static final DataFlavor WORD_FLAVOR = new DataFlavor(Word.class, "Word Object");
	private final Word word;

	public WordTransferable(Word word) {
		this.word = word;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { WORD_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return WORD_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (!WORD_FLAVOR.equals(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return word;
	}
}
