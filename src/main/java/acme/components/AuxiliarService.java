
package acme.components;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.components.SpamFilter.SpamFilter;
import acme.entities.currency_cache.CurrencyCache;
import acme.entities.systemconf.SystemConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class AuxiliarService {

	@Autowired
	private AuxiliarRepository repository;


	public boolean validatePrice(final Money price, final Integer minAm, final Integer maxAm) {
		return price.getAmount() >= minAm && price.getAmount() <= maxAm;
	}

	public boolean validateCurrency(final Money price) {
		final String aceptedCurrencies = this.repository.findSystemConfiguration().getAcceptedCurrencies();
		final List<String> currencies = Arrays.asList(aceptedCurrencies.split(","));
		return currencies.contains(price.getCurrency());
	}

	public boolean validatePrice(final Double price, final Integer minAm, final Double maxAm) {
		return price >= minAm && price < maxAm;
	}

	public boolean validateTextImput(final String input) {
		final SystemConfiguration sc = this.repository.findSystemConfiguration();
		final SpamFilter spamFilter = new SpamFilter(sc.getSpamWords(), sc.getSpamThreshold());
		return !spamFilter.isSpam(input);
	}

	public boolean validateDate(final Date date) {
		final Date maxDate = new Date(200, 11, 31, 23, 59);
		final Date minDate = new Date(100, 0, 1, 00, 00);
		return MomentHelper.isAfterOrEqual(date, minDate) && MomentHelper.isBeforeOrEqual(date, maxDate);
	}

	public String translateMoney(final Money money, final String lang) {
		String res;
		res = "";
		if (lang.equals("en")) {
			final double parteDecimal = money.getAmount() % 1;
			final double parteEntera = money.getAmount() - parteDecimal;
			res = parteEntera + "." + parteDecimal + " " + money.getCurrency();
		} else if (lang.equals("es"))
			res = money.getAmount() + " " + money.getCurrency();
		return res;
	}

	public Money changeCurrency(final Money money) {
		Money res;
		final String currentCurrency = this.repository.findSystemConfiguration().getSystemCurrency();
		res = new Money();
		if (money != null && !money.getCurrency().equals(currentCurrency)) {
			CurrencyCache cc;
			cc = this.repository.getCurrencyCacheByChange(money.getCurrency(), currentCurrency);
			if (cc == null || MomentHelper.isAfter(MomentHelper.getCurrentMoment(), MomentHelper.deltaFromMoment(cc.getDate(), 1, ChronoUnit.DAYS))) {
				final String apiBase = "https://api.freecurrencyapi.com/v1/latest?apikey=";
				final String apikey1 = "sTHyoIuiZKaQXMOomVo1r4AM5nr0frNRoXiJSKoj";
				//Tenemos varias apikeys por si se supera el limite de solicitudes, solo habría que cambiar key
				final String apikey2 = "IltvRNltXItpVmVkel1vys4oaQBZDpqOtYfqilz2";
				final String apikey3 = "gwuSNFH2RE98js5t8bC7hkKCkFWKnVL7CKK7IJX5";
				final String apikey4 = "sUcYZjfqDBI2A8sqbt7d5vpo2GkLaiMMbIAD0mTv";
				final String apikey5 = "gzai7Ka7lL8j7CGCfD0NoasAUzYuzbKHzurge092";
				final String requestURL = apiBase + apikey1 + "&currencies=" + money.getCurrency() + "&base_currency=" + currentCurrency;
				final OkHttpClient client = new OkHttpClient();
				final Request request = new Request.Builder().url(requestURL).build();
				Response response;
				try {
					response = client.newCall(request).execute();
					final String responseBody = response.body().string();
					final ObjectMapper mapper = new ObjectMapper();
					final JsonNode jsonNode = mapper.readTree(responseBody);
					final Double value = jsonNode.get("data").get(money.getCurrency()).asDouble();
					if (value != null) {
						res.setCurrency(currentCurrency);
						res.setAmount(value * money.getAmount());
					}
					if (cc == null) {
						cc = new CurrencyCache();
						cc.setDate(MomentHelper.getCurrentMoment());
						cc.setDestinationCurrency(currentCurrency);
						cc.setOrigenCurrency(money.getCurrency());
						cc.setRatio(value);
						this.repository.save(cc);
					} else {
						cc.setDate(MomentHelper.getCurrentMoment());
						cc.setRatio(value);
						this.repository.save(cc);
					}
				} catch (final Exception e) {
					res = money;
				}
			} else {
				res.setCurrency(currentCurrency);
				res.setAmount(cc.getRatio() * money.getAmount());
			}
		} else
			res = money;
		return res;
	}

}
