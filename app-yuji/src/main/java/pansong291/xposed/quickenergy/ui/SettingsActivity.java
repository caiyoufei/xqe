package pansong291.xposed.quickenergy.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import pansong291.xposed.quickenergy.R;
import pansong291.xposed.quickenergy.util.*;

public class SettingsActivity extends Activity {
  CheckBox cb_immediateEffect, cb_recordLog, cb_showToast,
      cb_stayAwake, cb_autoRestart,
      cb_collectEnergy, cb_helpFriendCollect, cb_receiveForestTaskAward,
      cb_cooperateWater,
      cb_enableFarm, cb_rewardFriend, cb_sendBackAnimal,
      cb_receiveFarmToolReward, cb_useNewEggTool, cb_harvestProduce,
      cb_donation, cb_answerQuestion, cb_receiveFarmTaskAward,
      cb_feedAnimal, cb_useAccelerateTool, cb_notifyFriend,
      cb_receivePoint, cb_openTreasureBox, cb_donateCharityCoin,
      cb_kbSignIn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Config.shouldReload = true;
    FriendIdMap.shouldReload = true;
    CooperationIdMap.shouldReload = true;

    cb_immediateEffect = findViewById(R.id.cb_immediateEffect);
    cb_recordLog = findViewById(R.id.cb_recordLog);
    cb_showToast = findViewById(R.id.cb_showToast);
    cb_stayAwake = findViewById(R.id.cb_stayAwake);
    cb_autoRestart = findViewById(R.id.cb_autoRestart);
    cb_collectEnergy = findViewById(R.id.cb_collectEnergy);
    cb_helpFriendCollect = findViewById(R.id.cb_helpFriendCollect);
    cb_receiveForestTaskAward = findViewById(R.id.cb_receiveForestTaskAward);
    cb_cooperateWater = findViewById(R.id.cb_cooperateWater);
    cb_enableFarm = findViewById(R.id.cb_enableFarm);
    cb_rewardFriend = findViewById(R.id.cb_rewardFriend);
    cb_sendBackAnimal = findViewById(R.id.cb_sendBackAnimal);
    cb_receiveFarmToolReward = findViewById(R.id.cb_receiveFarmToolReward);
    cb_useNewEggTool = findViewById(R.id.cb_useNewEggTool);
    cb_harvestProduce = findViewById(R.id.cb_harvestProduce);
    cb_donation = findViewById(R.id.cb_donation);
    cb_answerQuestion = findViewById(R.id.cb_answerQuestion);
    cb_receiveFarmTaskAward = findViewById(R.id.cb_receiveFarmTaskAward);
    cb_feedAnimal = findViewById(R.id.cb_feedAnimal);
    cb_useAccelerateTool = findViewById(R.id.cb_useAccelerateTool);
    cb_notifyFriend = findViewById(R.id.cb_notifyFriend);
    cb_receivePoint = findViewById(R.id.cb_receivePoint);
    cb_openTreasureBox = findViewById(R.id.cb_openTreasureBox);
    cb_donateCharityCoin = findViewById(R.id.cb_donateCharityCoin);
    cb_kbSignIn = findViewById(R.id.cb_kbSignIn);
  }

  @Override
  protected void onResume() {
    super.onResume();
    cb_immediateEffect.setChecked(Config.immediateEffect());
    cb_recordLog.setChecked(Config.recordLog());
    cb_showToast.setChecked(Config.showToast());
    cb_stayAwake.setChecked(Config.stayAwake());
    cb_autoRestart.setChecked(Config.autoRestart());
    cb_collectEnergy.setChecked(Config.collectEnergy());
    cb_helpFriendCollect.setChecked(Config.helpFriendCollect());
    cb_receiveForestTaskAward.setChecked(Config.receiveForestTaskAward());
    cb_cooperateWater.setChecked(Config.cooperateWater());
    cb_enableFarm.setChecked(Config.enableFarm());
    cb_rewardFriend.setChecked(Config.rewardFriend());
    cb_sendBackAnimal.setChecked(Config.sendBackAnimal());
    cb_receiveFarmToolReward.setChecked(Config.receiveFarmToolReward());
    cb_useNewEggTool.setChecked(Config.useNewEggTool());
    cb_harvestProduce.setChecked(Config.harvestProduce());
    cb_donation.setChecked(Config.donation());
    cb_answerQuestion.setChecked(Config.answerQuestion());
    cb_receiveFarmTaskAward.setChecked(Config.receiveFarmTaskAward());
    cb_feedAnimal.setChecked(Config.feedAnimal());
    cb_useAccelerateTool.setChecked(Config.useAccelerateTool());
    cb_notifyFriend.setChecked(Config.notifyFriend());
    cb_receivePoint.setChecked(Config.receivePoint());
    cb_openTreasureBox.setChecked(Config.openTreasureBox());
    cb_donateCharityCoin.setChecked(Config.donateCharityCoin());
    cb_kbSignIn.setChecked(Config.kbSginIn());
  }

  public void onClick(View v) {
    CheckBox cb = v instanceof CheckBox ? (CheckBox) v : null;
    Button btn = v instanceof Button ? (Button) v : null;
    switch (v.getId()) {
      //立即生效
      case R.id.cb_immediateEffect:
        Config.setImmediateEffect(cb.isChecked());
        break;
      //记录日志
      case R.id.cb_recordLog:
        Config.setRecordLog(cb.isChecked());
        break;
      //弹窗提示
      case R.id.cb_showToast:
        Config.setShowToast(cb.isChecked());
        break;
      //保持唤醒状态
      case R.id.cb_stayAwake:
        Config.setStayAwake(cb.isChecked());
        break;
      //自动重启服务
      case R.id.cb_autoRestart:
        Config.setAutoRestart(cb.isChecked());
        break;
      //收集能量
      case R.id.cb_collectEnergy:
        Config.setCollectEnergy(cb.isChecked());
        break;
      //检查间隔（分钟）
      case R.id.btn_checkInterval:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.CHECK_INTERVAL);
        break;
      //线程数量（不建议太多，建议默认）
      case R.id.btn_threadCount:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.THREAD_COUNT);
        break;
      //提前蹲守（毫秒，建议默认）
      case R.id.btn_advanceTime:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.ADVANCE_TIME);
        break;
      //偷取间隔（毫秒，建议默认）
      case R.id.btn_collectInterval:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.COLLECT_INTERVAL);
        break;
      //收取超时(秒，建议默认)
      case R.id.btn_collectTimeout:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.COLLECT_TIMEOUT);
        break;
      //偷x返水33克
      case R.id.btn_returnWater30:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.RETURN_WATER_30);
        break;
      //偷x返水18克
      case R.id.btn_returnWater20:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.RETURN_WATER_20);
        break;
      //偷x返水10克
      case R.id.btn_returnWater10:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.RETURN_WATER_10);
        break;
      //帮好友收取
      case R.id.cb_helpFriendCollect:
        Config.setHelpFriendCollect(cb.isChecked());
        break;
      //偷能量黑名单
      case R.id.btn_dontCollectList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getDontCollectList(), null);
        break;
      //帮能量黑名单
      case R.id.btn_dontHelpCollectList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getDontHelpCollectList(), null);
        break;
      //领取蚂蚁森林奖励
      case R.id.cb_receiveForestTaskAward:
        Config.setReceiveForestTaskAward(cb.isChecked());
        break;
      //浇水列表
      case R.id.btn_waterFriendList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getWaterFriendList(), Config.getWaterCountList());
        break;
      //合种浇水
      case R.id.cb_cooperateWater:
        Config.setCooperateWater(cb.isChecked());
        break;
      //合种浇水列表
      case R.id.btn_cooperateWaterList:
        ListDialog.show(this, btn.getText(), AlipayCooperate.getList(), Config.getCooperateWaterList(),
            Config.getcooperateWaterNumList());
        break;
      //启用农场
      case R.id.cb_enableFarm:
        Config.setEnableFarm(cb.isChecked());
        break;
      //cb_rewardFriend
      case R.id.cb_rewardFriend:
        Config.setRewardFriend(cb.isChecked());
        break;
      //自动赶鸡
      case R.id.cb_sendBackAnimal:
        Config.setSendBackAnimal(cb.isChecked());
        break;
      //赶鸡方式
      case R.id.btn_sendType:
        ChoiceDialog.showSendType(this, btn.getText());
        break;
      //不回报朋友列表
      case R.id.btn_dontSendFriendList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getDontSendFriendList(), null);
        break;
      //召回方式
      case R.id.btn_recallAnimalType:
        ChoiceDialog.showRecallAnimalType(this, btn.getText());
        break;
      //领取道具
      case R.id.cb_receiveFarmToolReward:
        Config.setReceiveFarmToolReward(cb.isChecked());
        break;
      //使用新的鸡蛋
      case R.id.cb_useNewEggTool:
        Config.setUseNewEggTool(cb.isChecked());
        break;
      //收获农产品
      case R.id.cb_harvestProduce:
        Config.setHarvestProduce(cb.isChecked());
        break;
      //捐赠
      case R.id.cb_donation:
        Config.setDonation(cb.isChecked());
        break;
      //答题
      case R.id.cb_answerQuestion:
        Config.setAnswerQuestion(cb.isChecked());
        break;
      //领取奖励
      case R.id.cb_receiveFarmTaskAward:
        Config.setReceiveFarmTaskAward(cb.isChecked());
        break;
      //喂鸡
      case R.id.cb_feedAnimal:
        Config.setFeedAnimal(cb.isChecked());
        break;
      //使用加速卡
      case R.id.cb_useAccelerateTool:
        Config.setUseAccelerateTool(cb.isChecked());
        break;
      //帮朋友名单
      case R.id.btn_feedFriendAnimalList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getFeedFriendAnimalList(),
            Config.getFeedFriendCountList());
        break;
      //通知赶鸡
      case R.id.cb_notifyFriend:
        Config.setNotifyFriend(cb.isChecked());
        break;
      //通知赶鸡黑名单
      case R.id.btn_dontNotifyFriendList:
        ListDialog.show(this, btn.getText(), AlipayUser.getList(), Config.getDontNotifyFriendList(), null);
        break;
      //Receive point
      case R.id.cb_receivePoint:
        Config.setReceivePoint(cb.isChecked());
        break;
      //打开宝箱
      case R.id.btn_donation_developer:
        Intent it2 = new Intent(Intent.ACTION_VIEW, Uri.parse(
            "alipays://platformapi/startapp?saId=10000007&qrcode=https%3A%2F%2Fqr.alipay.com%2Ffkx1504358uixr8gaa0jxb3"));
        startActivity(it2);
        break;
      //捐赠金币
      case R.id.cb_openTreasureBox:
        Config.setOpenTreasureBox(cb.isChecked());
        break;
      //Min exchange count
      case R.id.cb_donateCharityCoin:
        Config.setDonateCharityCoin(cb.isChecked());
        break;
      //Latest exchange time (24-hour system)
      case R.id.btn_minExchangeCount:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.MIN_EXCHANGE_COUNT);
        break;
      //口碑登录
      case R.id.btn_latestExchangeTime:
        EditDialog.showEditDialog(this, btn.getText(), EditDialog.EditMode.LATEST_EXCHANGE_TIME);
        break;
      //支持开发者
      case R.id.cb_kbSignIn:
        Config.setKbSginIn(cb.isChecked());
        break;
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (Config.hasChanged) {
      Config.hasChanged = !Config.saveConfigFile();
      Toast.makeText(this, "Configuration saved", Toast.LENGTH_SHORT).show();
    }
    FriendIdMap.saveIdMap();
    CooperationIdMap.saveIdMap();
  }
}

