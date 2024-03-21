package LinguaLink.guiComponents.wordTransferable;

import LinguaLink.components.word.Word;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class WordTransferable implements Transferable {
	public static final DataFlavor WORD_FLAVOR = new DataFlavor(Word.class, "Word Object");
	private final Word word;

	/**
	 * Constructs a WordTransferable with the specified Word object.
	 * @param word the Word object to be transferred
	 */
	public WordTransferable(Word word) {
		this.word = word;
	}

	/**
	 * Returns an array of DataFlavor objects indicating the flavors the data can be provided in.
	 *
	 * @return an array containing the supported DataFlavor
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { WORD_FLAVOR };
	}

	/**
	 * Returns whether or not the specified data flavor is supported for this object.
	 *
	 * @param flavor the requested flavor for the data
	 * @return true if the DataFlavor is supported; false otherwise
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return WORD_FLAVOR.equals(flavor);
	}

	/**
	 * Returns the transfer data contained in this object in the requested DataFlavor if possible.
	 *
	 * @param flavor the requested flavor for the data
	 * @return the data in the requested flavor
	 * @throws UnsupportedFlavorException if the requested data flavor is not supported
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (!WORD_FLAVOR.equals(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return word;
	}
}
