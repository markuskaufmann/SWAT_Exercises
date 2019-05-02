package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifier;
import ch.hslu.appe.fbs.business.authorisation.AuthorisationVerifierFactory;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.customer.CustomerPersistor;
import ch.hslu.appe.fbs.business.authorisation.model.UserRoles;
import ch.hslu.appe.fbs.model.db.Customer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
public final class CustomerManagerImplTest {

    private static AuthorisationVerifier authorisationVerifier;

    @Mock
    private CustomerPersistor customerPersistor;

    private CustomerManager customerManager;

    private UserDTO userTestee;
    private UserDTO userNotAuthorizedTestee;

    private List<Customer> customerTestees;

    @BeforeClass
    public static void setUpClass() {
        authorisationVerifier = AuthorisationVerifierFactory.createAuthorisationVerifier();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.customerManager = new CustomerManagerImpl(authorisationVerifier, this.customerPersistor);
        this.userTestee = getUserTestee();
        this.userNotAuthorizedTestee = getNotAuthorizedUserTestee();
        this.customerTestees = getCustomerTestees();
        when(this.customerPersistor.getAll()).thenReturn(this.customerTestees);
        when(this.customerPersistor.getById(this.customerTestees.get(0).getId())).thenReturn(Optional.of(this.customerTestees.get(0)));
    }

    @Test
    public void getAllCustomers_WhenCustomersExist_ReturnAllOfThem() throws UserNotAuthorisedException {
        final List<CustomerDTO> customers = this.customerManager.getAllCustomers(this.userTestee);

        assertEquals(this.customerTestees.size(), customers.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllCustomers_WhenUserIsNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        this.customerManager.getAllCustomers(this.userNotAuthorizedTestee);
    }

    @Test
    public void getCustomer_WhenCustomerWithGivenIdExists_ReturnIt() throws UserNotAuthorisedException {
        final Customer customerToFind = this.customerTestees.get(0);
        final CustomerDTO customerDTO = this.customerManager.getCustomer(customerToFind.getId(), this.userTestee);

        assertEquals(customerToFind.getId().intValue(), customerDTO.getId());
        assertEquals(customerToFind.getPrename(), customerDTO.getPrename());
        assertEquals(customerToFind.getSurname(), customerDTO.getSurname());
        assertEquals(customerToFind.getPlz().intValue(), customerDTO.getPlz());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getCustomer_WhenCustomerExistsButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        final Customer customerToFind = this.customerTestees.get(0);
        this.customerManager.getCustomer(customerToFind.getId(), this.userNotAuthorizedTestee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomer_WhenNoCustomerWithGivenIdExists_ThrowException() throws UserNotAuthorisedException {
        final Customer customerToFind = this.customerTestees.get(1);
        this.customerManager.getCustomer(customerToFind.getId(), this.userTestee);
    }

    @Test
    public void createCustomer_WhenNewCustomer_ThenCreateIt() throws UserNotAuthorisedException {
        final CustomerDTO customerDTOTestee = getCreateCustomerDTOTestee();

        doAnswer(invocationOnMock -> {
            final Customer customer = invocationOnMock.getArgument(0);
            this.customerTestees.add(customer);

            assertEquals(customerDTOTestee.getId(), customer.getId().intValue());
            assertEquals(customerDTOTestee.getPrename(), customer.getPrename());
            assertEquals(customerDTOTestee.getSurname(), customer.getSurname());
            assertEquals(customerDTOTestee.getPlz(), customer.getPlz().intValue());
            assertEquals(customerDTOTestee.getAddress(), customer.getAdress());
            assertEquals(customerDTOTestee.getCity(), customer.getCity());

            return null;
        }).when(this.customerPersistor).save(any(Customer.class));

        final int oldSizeCustomerTestees = this.customerTestees.size();
        this.customerManager.createCustomer(customerDTOTestee, this.userTestee);

        assertEquals(oldSizeCustomerTestees + 1, this.customerTestees.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void createCustomer_WhenCreateCustomerButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        final CustomerDTO customerDTOTestee = getCreateCustomerDTOTestee();
        this.customerManager.createCustomer(customerDTOTestee, this.userNotAuthorizedTestee);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SALESPERSON.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private UserDTO getNotAuthorizedUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(2, UserRoles.BRANCH_MANAGER.getRole());
        return new UserDTO(2, userRoleDTO, "fritzmeier");
    }

    private CustomerDTO getCreateCustomerDTOTestee() {
        return new CustomerDTO(1, "Max", "Muster", 6210, "Sursee", "Bahnhofstrasse 5");
    }

    private List<Customer> getCustomerTestees() {
        final List<Customer> customerTestees = new ArrayList<>();

        final Customer testee1 = new Customer();
        testee1.setId(1);
        testee1.setPrename("Max");
        testee1.setSurname("Muster");
        testee1.setPlz(6210);
        customerTestees.add(testee1);

        final Customer testee2 = new Customer();
        testee2.setId(2);
        testee2.setPrename("Fritz");
        testee2.setSurname("Meier");
        testee2.setPlz(6210);
        customerTestees.add(testee2);

        final Customer testee3 = new Customer();
        testee3.setId(3);
        testee3.setPrename("Herbert");
        testee3.setSurname("Mueller");
        testee3.setPlz(6210);
        customerTestees.add(testee3);

        return customerTestees;
    }
}
