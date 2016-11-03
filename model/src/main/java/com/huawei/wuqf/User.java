package com.huawei.wuqf;

import java.io.Serializable;

/**
 * @author wuqf
 */
public class User implements Serializable{

	private int id;
	private String userid;
	private String username;
	private String rootuserid;
	private String rootusername;
	private String mutualfollow;
	private int visited;
	private int infostored;


	public String getMutualfollow() {
		return mutualfollow;
	}

	public void setMutualfollow(String mutualfollow) {
		this.mutualfollow = mutualfollow;
	}

	public int getVisited() {
		return visited;
	}

	public void setVisited(int visited) {
		this.visited = visited;
	}

	public int getInfostored() {
		return infostored;
	}

	public void setInfostored(int infostored) {
		this.infostored = infostored;
	}


	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRootuserid() {
		return rootuserid;
	}
	public void setRootuserid(String rootuserid) {
		this.rootuserid = rootuserid;
	}
	public String getRootusername() {
		return rootusername;
	}
	public void setRootusername(String rootusername) {
		this.rootusername = rootusername;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
