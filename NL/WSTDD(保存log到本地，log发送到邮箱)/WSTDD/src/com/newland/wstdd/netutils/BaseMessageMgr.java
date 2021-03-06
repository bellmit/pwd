package com.newland.wstdd.netutils;

import com.newland.wstdd.find.bean.FindHotReq;
import com.newland.wstdd.find.bean.FindHotRes;
import com.newland.wstdd.find.bean.FindRecommendReq;
import com.newland.wstdd.find.bean.FindRecommendRes;
import com.newland.wstdd.find.categorylist.detail.bean.CollectReq;
import com.newland.wstdd.find.categorylist.detail.bean.CollectRes;
import com.newland.wstdd.find.categorylist.detail.bean.IsLikeAndCollectReq;
import com.newland.wstdd.find.categorylist.detail.bean.IsLikeAndCollectRes;
import com.newland.wstdd.find.categorylist.detail.bean.LikeReq;
import com.newland.wstdd.find.categorylist.detail.bean.LikeRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.CancelRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.GetEditRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.SubmitRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.CancelRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.GetEditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.SubmitRegistrationRes;
import com.newland.wstdd.find.find.bean.FindReq;
import com.newland.wstdd.find.find.bean.FindRes;
import com.newland.wstdd.find.hotlist.bean.FindCategoryReq;
import com.newland.wstdd.find.hotlist.bean.FindCategoryRes;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.beanrequest.CheckCodeReq;
import com.newland.wstdd.login.beanrequest.LoginBindReq;
import com.newland.wstdd.login.beanrequest.LoginReq;
import com.newland.wstdd.login.beanrequest.RegistFirstReq;
import com.newland.wstdd.login.beanrequest.RegistSecondReq;
import com.newland.wstdd.login.beanrequest.ThirdLoginReq;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.LoginBindRes;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.beanresponse.RegistSecondRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;
import com.newland.wstdd.mine.applyList.bean.RegistrationListReq;
import com.newland.wstdd.mine.applyList.bean.RegistrationListRes;
import com.newland.wstdd.mine.applyList.bean.UpdateRegistrationListReq;
import com.newland.wstdd.mine.applyList.bean.UpdateRegistrationListRes;
import com.newland.wstdd.mine.applyList.beanreq.MailUrlReq;
import com.newland.wstdd.mine.applyList.beanres.MailUrlRes;
import com.newland.wstdd.mine.beanrequest.DeleteActivityReq;
import com.newland.wstdd.mine.beanrequest.MinePersonInfoGetReq;
import com.newland.wstdd.mine.beanresponse.DeleteActivityRes;
import com.newland.wstdd.mine.beanresponse.MinePersonInfoGetRes;
import com.newland.wstdd.mine.managerpage.beanrequest.MyActivityListReq;
import com.newland.wstdd.mine.managerpage.beanrequest.OnTddRecommendReq;
import com.newland.wstdd.mine.managerpage.beanrequest.OpenActivityPeoplesReq;
import com.newland.wstdd.mine.managerpage.beanrequest.RegistrationCheckReq;
import com.newland.wstdd.mine.managerpage.beanrequest.RegistrationStateReq;
import com.newland.wstdd.mine.managerpage.beanresponse.MyActivityListRes;
import com.newland.wstdd.mine.managerpage.beanresponse.OnTddRecommendRes;
import com.newland.wstdd.mine.managerpage.beanresponse.OpenActivityPeoplesRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationCheckRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationStateRes;
import com.newland.wstdd.mine.managerpage.coversetting.ModifyCoverReq;
import com.newland.wstdd.mine.managerpage.coversetting.ModifyCoverRes;
import com.newland.wstdd.mine.managerpage.ilike.beanrequest.LikeListReq;
import com.newland.wstdd.mine.managerpage.ilike.beanresponse.LikeListRes;
import com.newland.wstdd.mine.managerpage.multitext.bean.SendMessageReq;
import com.newland.wstdd.mine.managerpage.multitext.bean.SendMessageRes;
import com.newland.wstdd.mine.minesetting.beanrequest.SafeReq;
import com.newland.wstdd.mine.minesetting.beanrequest.VersionReq;
import com.newland.wstdd.mine.minesetting.beanresponse.SafeRes;
import com.newland.wstdd.mine.minesetting.beanresponse.VersionRes;
import com.newland.wstdd.mine.minesetting.feedbackandhelp.bean.FeedBackReq;
import com.newland.wstdd.mine.minesetting.feedbackandhelp.bean.FeedBackRes;
import com.newland.wstdd.mine.myinterest.beanrequest.MyInterestTagsReq;
import com.newland.wstdd.mine.myinterest.beanresponse.MyInterestTagsRes;
import com.newland.wstdd.mine.personalcenter.bean.MineEditPersonReq;
import com.newland.wstdd.mine.personalcenter.bean.MineEditPersonRes;
import com.newland.wstdd.mine.personalcenter.beanreq.BindReq;
import com.newland.wstdd.mine.personalcenter.beanreq.RemoveBindReq;
import com.newland.wstdd.mine.personalcenter.beanres.BindRes;
import com.newland.wstdd.mine.personalcenter.beanres.RemoveBindRes;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineAddAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineDefaultAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineDeleteAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDefaultAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDeleteAddressRes;
//import com.newland.wstdd.mine.registrationlist.beanrequest.RegistrationListReq;
//import com.newland.wstdd.mine.registrationlist.beanrequest.UpdateRegistrationListReq;
//import com.newland.wstdd.mine.registrationlist.beanresponse.RegistrationListRes;
//import com.newland.wstdd.mine.registrationlist.beanresponse.UpdateRegistrationListRes;
import com.newland.wstdd.mine.twocode.beanrequest.TwoCodePayReq;
import com.newland.wstdd.mine.twocode.beanresponse.TwoCodePayRes;
import com.newland.wstdd.originate.beanrequest.OriginateFragmentReq;
import com.newland.wstdd.originate.beanrequest.OriginateSearchReq;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.OriginateFragmentRes;
import com.newland.wstdd.originate.beanresponse.OriginateSearchRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
import com.newland.wstdd.originate.origateactivity.beanrequest.SingleActivityPublishReq;
import com.newland.wstdd.originate.origateactivity.beanresponse.SingleActivityPublishRes;
import com.test.DeleteUserInfoReq;
import com.test.DeleteUserInfoRes;

/**
 * ??????????????????    ???????????????????????????
 * @author Administrator
 *
 */
public interface BaseMessageMgr {

	RetMsg<DeleteUserInfoRes> getCancelUserInfo(DeleteUserInfoReq deleteUserInfoReq);//0????????????    ????????????????????????
	RetMsg<LoginRes> getLoginInfo(LoginReq reqLoginInfo);//1??????????????????
	RetMsg<ThirdLoginRes> getThirdLoginInfo(ThirdLoginReq thirdLoginReq);//2???????????????
	RetMsg<RegistFirstRes> getRegistInfo(RegistFirstReq registFirstReq,String sessionId);//3??????????????????????????????
	RetMsg<LoginBindRes> getReqLoginBindInfo(LoginBindReq loginBindReq);//4?????????????????????
	RetMsg<RemoveBindRes> getRemoveBindInfo(RemoveBindReq removeBindReq);//4.4???????????????????????????
	RetMsg<BindRes> getBindInfo(BindReq bindReq);//4.4???????????????????????????
	RetMsg<CheckCodeRes> getCheckCodeIndo(CheckCodeReq checkCodeReq);//5?????????????????????
	RetMsg<RegistSecondRes> getRegistFinishInfo(RegistSecondReq registSecondReq, String userIdString);//6??????????????????????????????
	RetMsg<MyInterestTagsRes> getMyInterestInfo(MyInterestTagsReq myInterestTagsReq);//7????????????????????????
	RetMsg<MyInterestTagsRes> getMyInterestUpdateInfo(MyInterestTagsReq myInterestTagsReq);//7????????????????????????
	RetMsg<FindRes> getFindInfo(FindReq findReq);//8????????????????????????
	RetMsg<OriginateSearchRes> getOriginateSearchInfo(OriginateSearchReq originateSearchReq);//9?????????????????????
	RetMsg<VersionRes> getVersionInfo(VersionReq versionReq);//10??????????????????
	RetMsg<MinePersonInfoGetRes> getMinePersonInfoGetInfoMsg(MinePersonInfoGetReq minePersonInfoGetReq);//11??????????????????
	RetMsg<MineEditPersonRes> getMineEditPersonInfo(MineEditPersonReq mineEditPersonReq);//12??????????????????
	RetMsg<MineAddAddressRes> getMineReceiptAddressInfo(MineAddAddressReq mineAddAddressReq);//13??????????????????
	RetMsg<MineAddAddressRes> getMineUpdateAddressInfo(MineAddAddressReq mineAddAddressReq,String addressIdString);//??????????????????
	RetMsg<MineDefaultAddressRes> getMineDefaultAddressInfo(MineDefaultAddressReq mineDefaultAddressReq);//14???????????????????????????
	RetMsg<MineDeleteAddressRes> getMineDeleteAddressInfo(MineDeleteAddressReq mineDeleteAddressReq,String addressIdString);//15?????????????????????  
	RetMsg<FindHotRes> getFindHotListInfo(FindHotReq findHotReq);//16??????????????????
	RetMsg<FindRecommendRes> getFindRecommendListInfo(FindRecommendReq findRecommendReq);//17??????????????????
	RetMsg<SingleActivityRes> getSingleActivityInfo(SingleActivityReq singleActivityReq);//18????????????
	RetMsg<SingleActivityPublishRes> getSingleActivityPublishInfo(SingleActivityPublishReq singleActivityPublishReq, String activityAction, String activityId);//19??????????????????
	RetMsg<FindCategoryRes> getFindCategoeyInfo(FindCategoryReq findCategoryReq);//20????????????????????????
	RetMsg<SubmitRegistrationRes> getSubmitRegistrationInfo(SubmitRegistrationReq submitRegistrationReq, String string);//21??????????????????
	RetMsg<CancelRegistrationRes> getCancelRegistrationInfo(CancelRegistrationReq cancelRegistrationReq);//22????????????
	RetMsg<RegistrationListRes> getRegistrationListInfo(RegistrationListReq registrationListReq);//23????????????????????????
	RetMsg<UpdateRegistrationListRes> getUpdateRegistrationListInfo(UpdateRegistrationListReq updateRegistrationListReq);//24????????????????????????
	RetMsg<OpenActivityPeoplesRes> getOpenActivityPeoplesInfo(OpenActivityPeoplesReq openActivityPeoplesReq, String string);//25??????????????????????????????
	RetMsg<OnTddRecommendRes> getOnTddRecommendInfo(OnTddRecommendReq onTddRecommendReq, String string);//26????????????????????????
	RetMsg<RegistrationStateRes> getRegistrationStateInfo(RegistrationStateReq registrationStateReq, String string);//27????????????????????????
	RetMsg<RegistrationCheckRes> getRegistrationCheckInfo(RegistrationCheckReq registrationCheckReq, String string);//28??????????????????
	RetMsg<IsLikeAndCollectRes> getIsLikeAndCollectInfo(IsLikeAndCollectReq likeAndCollectReq);//29????????????????????????
	RetMsg<CollectRes> getCollectInfo(CollectReq collectReq);//30??????
	RetMsg<LikeRes> getLikeInfo(LikeReq likeReq);//31??????
	RetMsg<MyActivityListRes> getMyActivityListInfo(MyActivityListReq myActivityListReq);//??????????????? ????????? ??????????????????
	RetMsg<TwoCodePayRes> getTwoCodePayInfo(TwoCodePayReq twoCodePayReq,String string);
	RetMsg<SafeRes> getSafeInfo(SafeReq safeReq,String string);//????????????
	RetMsg<FeedBackRes> getFeedBackInfo(FeedBackReq feedBackReq);//????????????
	RetMsg<OriginateFragmentRes> getOriginateFragmentInfo(OriginateFragmentReq originateFragmentReq);//?????????????????????????????????
	RetMsg<MailUrlRes> getMailUrlInfo(MailUrlReq mailUrlReq,String id);//??????????????????????????????????????????
	RetMsg<SendMessageRes> getMultiTextInfo(SendMessageReq sendMessageReq,String id);//????????????
	RetMsg<GetEditRegistrationRes> getEditRegistrationInfo(GetEditRegistrationReq getEditRegistrationReq);//????????????????????????
	RetMsg<SubmitRegistrationRes> getOkEditRegistrationInfo(SubmitRegistrationReq submitRegistrationReq, String string);//??????????????????
	RetMsg<LikeListRes> getLikeListInfo(LikeListReq likeListReq);//??????????????????
	RetMsg<DeleteActivityRes> getDeleteActivityInfo(DeleteActivityReq deleteActivityReq, String string);//?????????????????????
	RetMsg<ModifyCoverRes> getModifyCoverInfo(ModifyCoverReq modifyCoverReq);//????????????
}
