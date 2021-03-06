package implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import abstracts.DataConverter;
import common.ExchangeRatesSeriesXml;
import entity.Currency;
import entity.CurrencyRate;
import exception.ParsingExchangeRate;

public class XmlConverter implements DataConverter {

	@Override
	public <T> CurrencyRate getCurrencyRate(T data) {
		// TODO Auto-generated method stub
		try {
			String dataString = (String) data;
			XmlMapper objectMapper = new XmlMapper();
			ExchangeRatesSeriesXml xml = objectMapper.readValue(dataString, ExchangeRatesSeriesXml.class);
			CurrencyRate rateModified = new CurrencyRate();
			Currency currency = new Currency();
			currency.setCurrencyCode(xml.getCode());
			currency.setCurrencyName(xml.getCurrency());
			rateModified.setCurrency(currency);
			rateModified.setCurrencyDate(xml.getRates().get(0).getEffectiveDate());
			rateModified.setCurrencyRate(xml.getRates().get(0).getMid());
			return rateModified;
		} catch(JsonMappingException e) {
			throw new ParsingExchangeRate(e.getMessage());
		} catch(JsonProcessingException e) {
			throw new ParsingExchangeRate(e.getMessage());
		}
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "xml";
	}
}
