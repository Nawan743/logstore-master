package com.cingo.logstore.repostory;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import com.cingo.logstore.entity.Log;

public class LogRepository extends Repository {

	public List<Log> findAllOrdened() {
		Query query = this.getManager().createQuery("SELECT e FROM Log e ORDER BY occurrences desc");
		return query.getResultList();
	}

	public Integer saveLog(Log log) {
		// Return code 1010 in fail and 1000 in success
		int code = 1010;
		
		Log dataLog = this.getManager().find(Log.class, log.getId());
		if (dataLog != null) {
			return code;
		}

		this.getManager().getTransaction().begin();
		this.getManager().persist(log);
		this.getManager().getTransaction().commit();
		this.getManager().close();
		code = 1000;

		return code;
	}

	@Transactional
	public Integer deleteLog(Integer id) {
		// Return code 1010 in fail and 1000 in success
		int code = 1010;

		Log dataLog = this.getManager().find(Log.class, id);
		if (dataLog != null) {
			this.getManager().getTransaction().begin();
			this.getManager().remove(dataLog);
			this.getManager().getTransaction().commit();
			this.getManager().close();
			code = 1000;
		}

		return code;
	}

	@Transactional
	public Integer updateLog(Log log) {
		// Return code 1010 in fail and 1000 in success
		int code = 1010;

		Log dataLog = this.getManager().find(Log.class, log.getId());
		if (dataLog != null) {
			this.getManager().getTransaction().begin();
			this.getManager().merge(log);
			this.getManager().getTransaction().commit();
			this.getManager().close();
			code = 1000;
		}

		return code;
	}

	public void add(Log log) {
		if (this.alreadyExists(log.getContent())) {
			Log firstLogFound = this.getLogs(log.getContent()).get(0);
			firstLogFound.newOcurrence(log.getOccurrences());
			this.update(firstLogFound);
		} else {
			super.add(log);
		}
	}

	private boolean alreadyExists(String content) {
		return !this.getLogs(content).isEmpty();
	}

	private List<Log> getLogs(String content) {
		Query query = this.getManager().createQuery("SELECT e FROM Log e WHERE content = :content");
		query.setParameter("content", content);
		return query.getResultList();
	}
}
