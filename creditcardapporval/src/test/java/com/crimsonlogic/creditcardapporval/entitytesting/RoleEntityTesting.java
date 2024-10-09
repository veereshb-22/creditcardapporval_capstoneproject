package com.crimsonlogic.creditcardapporval.entitytesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crimsonlogic.creditcardapporval.entity.Role;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleEntityTesting {
	 	
	@Autowired
	    private TestEntityManager entityManager;

	    private Role role;

	    @BeforeEach
	    public void setUp() {
	        // Initialize a Role instance
	        role = new Role();
	    }

	    @Test
	    public void testCreateAndRetrieveRole() {
	        // Test creating Role with roleID = "R01" and roleType = "Customer"
	        role.setRoleID("R01");
	        role.setRoleType("Customer");

	        // Persist the Role
	        Role savedRole = entityManager.persistAndFlush(role);

	        // Retrieve the Role
	        Role foundRole = entityManager.find(Role.class, savedRole.getRoleID());

	        // Assertions
	        assertNotNull(foundRole);
	        assertEquals(savedRole.getRoleID(), foundRole.getRoleID());
	        assertEquals(savedRole.getRoleType(), foundRole.getRoleType());
	    }

	    @Test
	    public void testRoleTypes() {
	        // Create roles and verify types
	        Role role1 = new Role("R01", "Customer");
	        Role role2 = new Role("R02", "SalesManager");
	        Role role3 = new Role("R03", "Manager");

	        // Persist and flush
	        entityManager.persistAndFlush(role1);
	        entityManager.persistAndFlush(role2);
	        entityManager.persistAndFlush(role3);

	        // Validate each role
	        Role foundRole1 = entityManager.find(Role.class, "R01");
	        Role foundRole2 = entityManager.find(Role.class, "R02");
	        Role foundRole3 = entityManager.find(Role.class, "R03");

	        // Assertions
	        assertNotNull(foundRole1);
	        assertEquals("Customer", foundRole1.getRoleType());

	        assertNotNull(foundRole2);
	        assertEquals("SalesManager", foundRole2.getRoleType());

	        assertNotNull(foundRole3);
	        assertEquals("Manager", foundRole3.getRoleType());
	    }
}
