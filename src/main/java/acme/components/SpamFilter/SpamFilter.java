
package acme.components.SpamFilter;

import java.util.Arrays;
import java.util.List;

public class SpamFilter {

	String	spamWords;

	double	threshold;

	int		wordsCount;


	public SpamFilter(final String spamWords, final double threshold) {
		this.spamWords = spamWords;
		this.threshold = threshold;
	}

	public boolean isSpam(final String input) {
		final String oneSpaceWords;
		final String[] inputWords;
		this.wordsCount = 0;
		oneSpaceWords = input.toLowerCase().replaceAll("\\s+", " ");	//Cambio espacios m�ltiples por espacios
		inputWords = oneSpaceWords.split(" ");		//Separo las palabras por espacios
		final List<String> listOfImputWords = Arrays.asList(inputWords);		//Convierto el array en una lista para recorrerla f�cilmente
		final String spamWordsAux = this.spamWords.replaceAll("\\s+", " ").replaceAll(", ", ",");
		final String[] arrayOfSpamWords = spamWordsAux.toLowerCase().split(",");		//Separo las palabras spam
		final List<String> listOfSpamWords = Arrays.asList(arrayOfSpamWords);		//Las meto en un array
		for (final String word : listOfSpamWords)		//Recorremos las palabras
			this.wordsCount += oneSpaceWords.split(word, -1).length - 1;		//Partimos la l�nea string utilizando como caracter una palabra de spam y contamos los trozos
		final double umbral = (double) this.wordsCount / listOfImputWords.size();
		return umbral > this.threshold;		//Si el umbral es mayor que threshold se indicar� como Spam
	}

}
