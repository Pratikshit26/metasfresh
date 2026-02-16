package de.metas.phonecall.process;

import java.time.LocalDate;

import org.compiere.SpringContextHolder;

import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.service.PhonecallScheduleService;
import de.metas.phonecall.service.PhonecallSchemaRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class C_Phonecall_Schema_GenerateSchedules extends JavaProcess implements IProcessPrecondition
{
	private final PhonecallScheduleService phonecallScheduleService = SpringContextHolder.instance.getBean(PhonecallScheduleService.class);
	private final PhonecallSchemaRepository schemaRepo = SpringContextHolder.instance.getBean(PhonecallSchemaRepository.class);

	@Param(parameterName = "DateFrom")
	private LocalDate p_StartDate;

	@Param(parameterName = "DateTo")
	private LocalDate p_EndDate;

	@Override
	protected String doIt()
	{
		final PhonecallSchema phonecallSchema = schemaRepo.getById(PhonecallSchemaId.ofRepoId(getRecord_ID()));

		phonecallScheduleService.generatePhonecallSchedulesForSchema(phonecallSchema, p_StartDate, p_EndDate);

		return MSG_OK;
	}


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if(!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
