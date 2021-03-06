package com.thegoate.utils.fill.serialize.pojos;

import com.thegoate.utils.fill.serialize.DefaultSource;
import com.thegoate.utils.fill.serialize.GoatePojo;
import com.thegoate.utils.fill.serialize.GoateSource;

/**
 * Created by Eric Angeli on 6/18/2020.
 */
@GoatePojo
public class FlatInner {

	@GoateSource(source = FlatNest.class, key = "inner_name")
	private String name;
	@GoateSource(source = FlatNest.class, key = "inner_job")
	private String job;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
}
