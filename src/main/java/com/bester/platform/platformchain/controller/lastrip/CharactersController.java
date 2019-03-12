package com.bester.platform.platformchain.controller.lastrip;

import com.google.gson.JsonElement;
import com.gxchain.client.GXChainClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwen
 * @date 2019/3/8
 */
@RestController
public class CharactersController {

    private static final String TEST_PRIVATE_KEY = "5K9veg5tG7fhsPepfvg8y8eRwMTLPwMTsJURDMUa52ZpZPBCCKc";
    private static final String TEST_ACCOUNT_NAME = "morse1113";
    private static final String TEST_POINT = "https://testnet.gxchain.org";
    private static final String TEST_CONTRACT = "genesis-lw";

    private static GXChainClient gxChainClient = new GXChainClient(TEST_PRIVATE_KEY, TEST_ACCOUNT_NAME, TEST_POINT);

    @GetMapping("/genesis/characterCount")
    public String characterCount() {
        final String table = "characount";
        JsonElement characterCount = gxChainClient.getTableRows(TEST_CONTRACT, table, 0, 1);
        return characterCount.toString();
    }

    @GetMapping("/genesis/character")
    public String characters(int id) {
        final String table = "character";
        JsonElement characterCount = gxChainClient.getTableRows(TEST_CONTRACT, table, id, 1);
        return characterCount.toString();
    }
}
