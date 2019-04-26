package ch.hslu.appe.fbs.remote.service.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        final Optional<UserDTO> optUserDTO = this.userSessionMap.getUserSession(this.clientHost.getHostAddress());
        if(optUserDTO.isPresent()) {
            return this.itemManager.getAllItems(optUserDTO.get());
        }
        return Collections.emptyList();
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getItemServiceName();
    }
}
