package com.ldx.javaSpringBoot.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.account.entity.Role;
import com.ldx.javaSpringBoot.modules.account.service.RoleService;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 127.0.0.1/api/roles
	 */
	@RequestMapping("/roles")
	List<Role> getRoles() {
		return roleService.getRoles();
	}
	
	@PostMapping(value = "/roles", consumes = "application/json")
	public PageInfo<Role> getRoles(@RequestBody SearchVo searchVo) {
		return roleService.getRoles(searchVo);
	}
	
	@PostMapping(value = "/role", consumes = "application/json")
	public Result<Role> insertRole(@RequestBody Role role) {
		return roleService.editRole(role);
	}
	
	@PutMapping(value = "/role", consumes = "application/json")
	public Result<Role> updateRole(@RequestBody Role role) {
		return roleService.editRole(role);
	}
	
	@RequestMapping("/role/{roleId}")
	public Role getRole(@PathVariable int roleId) {
		return roleService.getRoleById(roleId);
	}
	
	@DeleteMapping("/role/{roleId}")
	public Result<Role> deleteRole(@PathVariable int roleId) {
		return roleService.deleteRole(roleId);
	}
}
