package com.fpnn.rtm.api;

import com.fpnn.rtm.RTMException;
import com.fpnn.rtm.RTMServerClientBase;
import com.fpnn.sdk.AnswerCallback;
import com.fpnn.sdk.ErrorCode;
import com.fpnn.sdk.ErrorRecorder;
import com.fpnn.sdk.proto.Answer;
import com.fpnn.sdk.proto.Quest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface GroupAPI extends APIBase {

    default void addGroupMembers(long gid, Set<Long> uids)
        throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        addGroupMembers(gid, uids, 0);
    }

    default void addGroupMembers(long gid, Set<Long> uids, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("addgroupmembers");
        quest.param("gid", gid);
        quest.param("uids", uids);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void addGroupMembers(long gid, Set<Long> uids, DoneLambdaCallback callback) {
        addGroupMembers(gid, uids, callback, 0);
    }

    default void addGroupMembers(long gid, Set<Long> uids, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("addgroupmembers");
        }catch (Exception ex){
            ErrorRecorder.record("Generate addgroupmembers message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate addgroupmembers message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uids", uids);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    default void delGroupMembers(long gid, Set<Long> uids)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        delGroupMembers(gid, uids, 0);
    }

    default void delGroupMembers(long gid, Set<Long> uids, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("delgroupmembers");
        quest.param("gid", gid);
        quest.param("uids", uids);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void delGroupMembers(long gid, Set<Long> uids, DoneLambdaCallback callback) {
        addGroupMembers(gid, uids, callback, 0);
    }

    default void delGroupMembers(long gid, Set<Long> uids, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("delgroupmembers");
        }catch (Exception ex){
            ErrorRecorder.record("Generate delgroupmembers message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate delgroupmembers message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uids", uids);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    default void delGroup(long gid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        delGroup(gid, 0);
    }

    default void delGroup(long gid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("delgroup");
        quest.param("gid", gid);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void delGroup(long gid, DoneLambdaCallback callback) {
        delGroup(gid, callback, 0);
    }

    default void delGroup(long gid, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("delgroup");
        }catch (Exception ex){
            ErrorRecorder.record("Generate delgroup message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate delgroup message sign exception.");
            return;
        }
        quest.param("gid", gid);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    interface GetGroupMembersLambdaCallBack{
        void done(Set<Long> uids, int errorCode, String errorMessage);
    }

    default Set<Long> getGroupMembers(long gid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        return getGroupMembers(gid, 0);
    }

    default Set<Long> getGroupMembers(long gid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("getgroupmembers");
        quest.param("gid", gid);
        Answer answer = client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
        Object object = answer.get("uids", null);
        Set<Long> result = new HashSet<>();
        if(object != null){
            List<Object> data = (List<Object>)object;
            for(Object o : data){
                result.add(Long.valueOf(String.valueOf(o)));
            }
        }
        return result;
    }

    default void getGroupMembers(long gid, GetGroupMembersLambdaCallBack callback) {
        getGroupMembers(gid, callback, 0);
    }

    default void getGroupMembers(long gid, GetGroupMembersLambdaCallBack callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("getgroupmembers");
        }catch (Exception ex){
            ErrorRecorder.record("Generate getgroupmembers message sign exception.", ex);
            callback.done(null, ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate getgroupmembers message sign exception.");
            return;
        }
        quest.param("gid", gid);
        AnswerCallback answerCallback = new AnswerCallback() {
            @Override
            public void onAnswer(Answer answer) {
                Object object = answer.get("uids", null);
                Set<Long> result = new HashSet<>();
                if(object != null){
                    List<Object> data = (List<Object>)object;
                    for(Object o : data){
                        result.add(Long.valueOf(String.valueOf(o)));
                    }
                }
                callback.done(result, ErrorCode.FPNN_EC_OK.value(), "");
            }

            @Override
            public void onException(Answer answer, int i) {
                String info = null;
                if(answer != null){
                    info = (String) answer.get("ex", "");
                }
                callback.done(null, i, info);
            }
        };
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    interface IsGroupMemberCallBack{
        void done(boolean ok, int errorCode, String errorMessage);
    }

    default boolean isGroupMember(long uid, long gid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        return isGroupMember(uid, gid, 0);
    }

    default boolean isGroupMember(long uid, long gid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("isgroupmember");
        quest.param("gid", gid);
        quest.param("uid", uid);
        Answer answer = client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
        return (boolean)answer.get("ok", false);
    }

    default void isGroupMember(long uid, long gid, IsGroupMemberCallBack callback) {
        isGroupMember(uid, gid, callback, 0);
    }

    default void isGroupMember(long uid, long gid, IsGroupMemberCallBack callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("isgroupmember");
        }catch (Exception ex){
            ErrorRecorder.record("Generate isgroupmember message sign exception.", ex);
            callback.done(false, ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate isgroupmember message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uid", uid);
        AnswerCallback answerCallback = new AnswerCallback() {
            @Override
            public void onAnswer(Answer answer) {
                callback.done((boolean)answer.get("ok", false), ErrorCode.FPNN_EC_OK.value(), "");
            }

            @Override
            public void onException(Answer answer, int i) {
                String info = null;
                if(answer != null){
                    info = (String) answer.get("ex", "");
                }
                callback.done(false, i, info);
            }
        };
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    interface GetUserGroupsCallBack{
        void done(Set<Long> gids, int errorCode, String errorMessage);
    }

    default Set<Long> getUserGroups(long uid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        return getUserGroups(uid, 0);
    }

    default Set<Long> getUserGroups(long uid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("getusergroups");
        quest.param("uid", uid);
        Answer answer = client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
        Object object = answer.get("gids", null);
        Set<Long> result = new HashSet<>();
        if(object != null){
            List<Object> data = (List<Object>)object;
            for(Object o : data){
                result.add(Long.valueOf(String.valueOf(o)));
            }
        }
        return result;
    }

    default void getUserGroups(long uid, GetUserGroupsCallBack callback) {
        getUserGroups(uid, callback, 0);
    }

    default void getUserGroups(long uid, GetUserGroupsCallBack callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("getusergroups");
        }catch (Exception ex){
            ErrorRecorder.record("Generate getusergroups message sign exception.", ex);
            callback.done(null, ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate getusergroups message sign exception.");
            return;
        }
        quest.param("uid", uid);
        AnswerCallback answerCallback = new AnswerCallback() {
            @Override
            public void onAnswer(Answer answer) {
                Object object = answer.get("gids", null);
                Set<Long> result = new HashSet<>();
                if(object != null){
                    List<Object> data = (List<Object>)object;
                    for(Object o : data){
                        result.add(Long.valueOf(String.valueOf(o)));
                    }
                }
                callback.done(result, ErrorCode.FPNN_EC_OK.value(), "");
            }

            @Override
            public void onException(Answer answer, int i) {
                String info = null;
                if(answer != null){
                    info = (String) answer.get("ex", "");
                }
                callback.done(null, i, info);
            }
        };
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    default void addGroupBan(long gid, long uid, int btime)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        addGroupBan(gid, uid, btime, 0);
    }

    default void addGroupBan(long gid, long uid, int btime, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("addgroupban");
        quest.param("gid", gid);
        quest.param("uid", uid);
        quest.param("btime", btime);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void addGroupBan(long gid, long uid, int btime, DoneLambdaCallback callback) {
        addGroupBan(gid, uid, btime, callback,0);
    }

    default void addGroupBan(long gid, long uid, int btime, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("addgroupban");
        }catch (Exception ex){
            ErrorRecorder.record("Generate addgroupban message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate addgroupban message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uid", uid);
        quest.param("btime", btime);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    default void removeGroupBan(long gid, long uid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        removeGroupBan(gid, uid,0);
    }

    default void removeGroupBan(long gid, long uid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("removegroupban");
        quest.param("gid", gid);
        quest.param("uid", uid);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void removeGroupBan(long gid, long uid, DoneLambdaCallback callback) {
        removeGroupBan(gid, uid, callback,0);
    }

    default void removeGroupBan(long gid, long uid, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("removegroupban");
        }catch (Exception ex){
            ErrorRecorder.record("Generate removegroupban message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate removegroupban message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uid", uid);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    interface IsBanOfGroupCallBack{
        void done(boolean ok, int errorCode, String errorMessage);
    }

    default boolean isBanOfGroup(long uid, long gid)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        return isBanOfGroup(uid, gid, 0);
    }

    default boolean isBanOfGroup(long uid, long gid, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("isgroupmember");
        quest.param("gid", gid);
        quest.param("uid", uid);
        Answer answer = client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
        return (boolean)answer.get("ok", false);
    }

    default void isBanOfGroup(long uid, long gid, IsBanOfGroupCallBack callback) {
        isBanOfGroup(uid, gid, callback, 0);
    }

    default void isBanOfGroup(long uid, long gid, IsBanOfGroupCallBack callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("isgroupmember");
        }catch (Exception ex){
            ErrorRecorder.record("Generate isgroupmember message sign exception.", ex);
            callback.done(false, ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate isgroupmember message sign exception.");
            return;
        }
        quest.param("gid", gid);
        quest.param("uid", uid);
        AnswerCallback answerCallback = new AnswerCallback() {
            @Override
            public void onAnswer(Answer answer) {
                callback.done((boolean)answer.get("ok", false), ErrorCode.FPNN_EC_OK.value(), "");
            }

            @Override
            public void onException(Answer answer, int i) {
                String info = null;
                if(answer != null){
                    info = (String) answer.get("ex", "");
                }
                callback.done(false, i, info);
            }
        };
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    default void setGroupInfo(long gid, String openInfo, String priInfo)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        setGroupInfo(gid, openInfo, priInfo, 0);
    }

    default void setGroupInfo(long gid, String openInfo, String priInfo, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("setgroupinfo");
        quest.param("gid", gid);
        if(openInfo != null && openInfo.length() > 0)
            quest.param("oinfo", openInfo);
        if(priInfo != null && priInfo.length() > 0)
            quest.param("pinfo", priInfo);
        client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
    }

    default void setGroupInfo(long gid, String openInfo, String priInfo, DoneLambdaCallback callback) {
        setGroupInfo(gid, openInfo, priInfo, callback,0);
    }

    default void setGroupInfo(long gid, String openInfo, String priInfo, DoneLambdaCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("setgroupinfo");
        }catch (Exception ex){
            ErrorRecorder.record("Generate setgroupinfo message sign exception.", ex);
            callback.done(ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate setgroupinfo message sign exception.");
            return;
        }
        quest.param("gid", gid);
        if(openInfo != null && openInfo.length() > 0)
            quest.param("oinfo", openInfo);
        if(priInfo != null && priInfo.length() > 0)
            quest.param("pinfo", priInfo);
        AnswerCallback answerCallback = new FPNNDoneLambdaCallbackWrapper(callback);
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

    interface GetGroupInfoCallback{
        void done(String openInfo, String priInfo, int errorCode, String errorMessage);
    }

    default void getGroupInfo(long gid, StringBuffer openInfo, StringBuffer priInfo)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        getGroupInfo(gid, openInfo, priInfo, 0);
    }

    default void getGroupInfo(long gid, StringBuffer openInfo, StringBuffer priInfo, int timeoutInseconds)
            throws RTMException, GeneralSecurityException, IOException, InterruptedException{
        RTMServerClientBase client = getCoreClient();
        Quest quest = client.genBasicQuest("getgroupinfo");
        quest.param("gid", gid);
        Answer answer = client.sendQuestAndCheckAnswer(quest, timeoutInseconds);
        openInfo.setLength(0);
        priInfo.setLength(0);
        openInfo.append((String)answer.get("oinfo", ""));
        priInfo.append((String)answer.get("pinfo", ""));
    }

    default void getGroupInfo(long gid, GetGroupInfoCallback callback) {
        getGroupInfo(gid, callback,0);
    }

    default void getGroupInfo(long gid, GetGroupInfoCallback callback, int timeoutInseconds){
        RTMServerClientBase client = getCoreClient();
        Quest quest;
        try{
            quest = client.genBasicQuest("getgroupinfo");
        }catch (Exception ex){
            ErrorRecorder.record("Generate getgroupinfo message sign exception.", ex);
            callback.done("", "", ErrorCode.FPNN_EC_CORE_UNKNOWN_ERROR.value(), "Generate getgroupinfo message sign exception.");
            return;
        }
        quest.param("gid", gid);
        AnswerCallback answerCallback = new AnswerCallback() {
            @Override
            public void onAnswer(Answer answer) {
                callback.done((String)answer.get("oinfo", ""), (String)answer.get("pinfo", ""), ErrorCode.FPNN_EC_OK.value(), "");
            }

            @Override
            public void onException(Answer answer, int i) {
                String info = null;
                if(answer != null){
                    info = (String)answer.get("ex", "");
                }
                callback.done("", "", i, info);

            }
        };
        client.sendQuest(quest, answerCallback, timeoutInseconds);
    }

}
