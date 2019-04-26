package ch.hslu.appe.fbs.remote.service.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.remote.session.UserSessionVerifier;

import java.rmi.RemoteException;
import java.util.List;

public final class ItemServiceImpl implements ItemService {

    private final ClientHost clientHost;
    private final UserSessionMap userSessionMap;

    private final ItemManager itemManager;

    public ItemServiceImpl(final ClientHost clientHost, final UserSessionMap userSessionMap, final ItemManager itemManager) {
        this.clientHost = clientHost;
        this.userSessionMap = userSessionMap;
        this.itemManager = itemManager;
    }

    @Override
    public List<ItemDTO> getAllItems() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.itemManager.getAllItems(userDTO);
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getItemServiceName();
    }

    private UserDTO getUserSession() {
        return UserSessionVerifier.getUserSession(this.userSessionMap, this.clientHost);
    }
}
