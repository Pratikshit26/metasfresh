package de.metas.phonecall.process;

import org.compiere.Adempiere;

import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallScheduleId;
import de.metas.phonecall.service.PhonecallScheduleRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class C_Phonecall_Schedule_MarkAsCalled extends JavaProcess implements IProcessPrecondition
{
	private final PhonecallScheduleRepository phonecallSchedueRepo = Adempiere.getBean(PhonecallScheduleRepository.class);

	@Override
	protected String doIt()
	{
		final PhonecallSchedule phonecallSchedule = phonecallSchedueRepo.getById(PhonecallScheduleId.ofRepoId(getRecord_ID()));
		phonecallSchedueRepo.markAsCalled(phonecallSchedule);

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
