package com.cingo.logstore.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.cingo.logstore.entity.Log;
import com.cingo.logstore.repostory.LogRepository;

@Path("log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {

	@Context
	private HttpServletRequest httpRequest;
	private LogRepository repository = new LogRepository();

	@GET
	public List<Log> getLogs() {
		return this.repository.findAllOrdened();
	}

	@POST
	@Path("/save")
	public Integer saveLog(Log log) {
		return this.repository.saveLog(log);
	}

	@DELETE
	@Path("/delete/{id}")
	public Integer deleteLog(@PathParam("id") Integer id){
		return this.repository.deleteLog(id);
	}

	@PUT
	@Path("/update")
	public Integer updateLog(Log log) {
		return this.repository.updateLog(log);
	}
}
