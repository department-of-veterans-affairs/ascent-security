package gov.va.ascent.security.transform.impl;

import java.util.List;

import gov.va.ascent.framework.exception.AscentRuntimeException;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.jwt.correlation.CorrelationIdsParser;
import gov.va.ascent.security.model.Person;
import gov.va.ascent.security.transform.AbstractProviderSecurityTransformer;

public class PersonToPersonTraitsTranformer extends AbstractProviderSecurityTransformer<Person, PersonTraits> {

	static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(PersonToPersonTraitsTranformer.class);

	@Override
	public PersonTraits transformToService(final Person toTransform) {
		PersonTraits personTraits = null;

		if (toTransform == null) {
			throw new AscentRuntimeException("Cannot transform a null Person model object.");
		} else {
			personTraits = new PersonTraits();

			personTraits.setFirstName(toTransform.getFirstName());
			personTraits.setLastName(toTransform.getLastName());
			personTraits.setPrefix(toTransform.getPrefix());
			personTraits.setMiddleName(toTransform.getMiddleName());
			personTraits.setSuffix(toTransform.getSuffix());
			personTraits.setBirthDate(toTransform.getBirthDate());
			personTraits.setGender(toTransform.getGender());
			personTraits.setAssuranceLevel(toTransform.getAssuranceLevel());
			personTraits.setEmail(toTransform.getEmail());

			try {
				CorrelationIdsParser instance = new CorrelationIdsParser();
				@SuppressWarnings("unchecked")
				List<String> list = toTransform.getCorrelationIds();
				instance.parseCorrelationIds(list, personTraits);

			} catch (Exception e) { // NOSONAR intentionally wide, errors are already logged
				// if there is any detected issue with the correlation ids
				LOGGER.error("Unable to parse correlation Ids", e);
				throw new AscentRuntimeException("Unable to parse correlation Ids", e);
			}
		}

		return personTraits;
	}

	@Override
	public Person transformToProvider(final PersonTraits toTransform) {
		throw new IllegalAccessError(
				"Method not impelemented: Person transformToProvider(PersonTraits toTransform)");
	}

}
