package alankzh.blog.rediskeys.rest.controller;

import alankzh.blog.base.api.BaseResponse;
import alankzh.blog.base.api.ResultCode;
import alankzh.blog.rediskeys.core.BankcardEntity;
import alankzh.blog.rediskeys.core.manager.BankcardManagerMaster;
import alankzh.blog.rediskeys.core.manager.BankcardManagerWrong;
import alankzh.blog.rediskeys.core.manager.BankcardManagerPrefect;
import alankzh.blog.rediskeys.core.manager.BankcardManagerBetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankcard")
@Slf4j
public class BankCardController {

    @Autowired
    private BankcardManagerMaster bankcardManager;

    @PostMapping("/add")
    public BaseResponse addBankcard(@RequestParam("customerId") String customerId,
                                    @RequestParam("indexCardId") String indexCardId,
                                    @RequestParam("mobile") String mobile,
                                    @RequestParam(value = "infomation", required = false) String information){
        int ef = bankcardManager.insert(new BankcardEntity()
                .setCustomerId(customerId)
                .setIndexCardId(indexCardId)
                .setMobile(mobile)
                .setInformation(information));
        if (ef > 0){
            return BaseResponse.buildWithResultCode(ResultCode.SUCCESS);
        } else {
            return BaseResponse.buildWithResultCode(ResultCode.FAILURE);
        }
    }

    @GetMapping("/get-by-cid")
    public BaseResponse<List<BankcardEntity>> getByCustomerId(@RequestParam("customerId") String customerId) {
        List<BankcardEntity> list = bankcardManager.findByCustomerId(customerId);
        return new BaseResponse<>(list);
    }

    @GetMapping("/get-by-idx")
    public BaseResponse<BankcardEntity> getByIndexCard(@RequestParam("indexCardId")  String indexCardId){
        BankcardEntity bankcardEntity = bankcardManager.findByIndexCardId(indexCardId);
        return new BaseResponse<>(bankcardEntity);
    }

    @PostMapping("/update-info-by-idx")
    public BaseResponse updateInfoByIndexCardId(@RequestParam("information") String info,
                                                @RequestParam("indexCardId")  String indexCardId){
//        BankcardEntity bankcardEntity = bankcardManager.findByIndexCardId(indexCardId);
//        if (bankcardEntity == null){
//            return BaseResponse.buildWithResultCode(ResultCode.DATA_NOT_EXIST);
//        }
//        int ef = bankcardManager.updateInformationByIndexCardId(info, indexCardId, bankcardEntity.getCustomerId());

      int ef = bankcardManager.updateInformationByIndexCardId(info, indexCardId);
        if (ef > 0){
            return BaseResponse.buildWithResultCode(ResultCode.SUCCESS);
        } else {
            return BaseResponse.buildWithResultCode(ResultCode.FAILURE);
        }
    }

    @PostMapping("/update-info-by-cid")
    public BaseResponse updateInfoByCid(@RequestParam("information") String info,
                                        @RequestParam("customerId")  String customerId){

//        List<BankcardEntity> bankcardEntityList = bankcardManager.findByCustomerId(customerId);
//        if (bankcardEntityList.size() <= 0){
//            return BaseResponse.buildWithResultCode(ResultCode.DATA_NOT_EXIST);
//        }
//        int ef = bankcardManager.updateInformationByCid(info, bankcardEntityList);

        int ef = bankcardManager.updateInformationByCid(info, customerId);
        if (ef > 0){
            return BaseResponse.buildWithResultCode(ResultCode.SUCCESS);
        } else {
            return BaseResponse.buildWithResultCode(ResultCode.FAILURE);
        }
    }

}
