
package acme.components.SpamFilter;

import java.util.Arrays;
import java.util.List;

public class SpamFilter2 {

	String	spamWords;

	double	threshold;

	int		wordsCount;


	public SpamFilter2(final String spamWords, final double threshold) {
		this.spamWords = spamWords;
		this.threshold = threshold;
	}

	public boolean isSpam(final String input) {
		final String oneSpaceWords;
		final String[] inputWords;
		this.wordsCount = 0;
		oneSpaceWords = input.toLowerCase().replaceAll("\\s+", " ");//Cambio espacios multiples por espacios
		inputWords = oneSpaceWords.split(" ");//Separo las palabras por espacios
		final List<String> listOfInputWords = Arrays.asList(inputWords);//combierto el array en una lista para recorrela facilmente
		final String spamWordsAux = this.spamWords.replaceAll("\\s+", " ").replaceAll(", ", ",");
		final String[] arrayOfSpamWords = spamWordsAux.toLowerCase().split(",");//Separo las palabras spam
		final List<String> listOfSpamWords = Arrays.asList(arrayOfSpamWords);//Las meto en un array
		for (final String word : listOfSpamWords)
			this.wordsCount += oneSpaceWords.split(word, -1).length - 1;
		final double umbral = (double) this.wordsCount / listOfInputWords.size();
		return umbral > this.threshold;//Si umbral es mayor que threshold Tenemos Spam
	}

}
