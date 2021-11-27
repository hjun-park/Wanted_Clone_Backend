//package com.example.demo;
//
//import com.example.demo.src.client.model.Client;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//@ExtendWith(SpringExtension.class) // Junit4의 RunWith와 같음
//@SpringBootTest
//public class ClientDAOTest {
//
//	@Autowired
//	ClientDAO clientDAO;
//
//	@Test
//	@Transactional
//	@Rollback(true)	// 내 눈을 의심하고 롤백 노노 원할 때
//	public void testClient() throws Exception {
//		//given
//		Client client = Client.builder()
//		.email("p11@gmail.com")
//		.password("pwd1231233123")
//		.name("t11")
//		.phoneNumber("012313412412")
//		.profileImageUrl(null)
//		.birthDate("19961022")
////		.addressBuildingNum("0")
////		.districtCode("0")
//		.build();
//
//		//when
//		Long clientId = clientDAO.createClient(client);
//		Client findClient = clientDAO.findClientById(clientId);
//
//	    //then
//		Assertions.assertThat(findClient.getId()).isEqualTo(client.getId());
//		Assertions.assertThat(findClient.getName()).isEqualTo(client.getName());
//
//
//	}
//
//
//
//
//}
