package com.kripeshpoudel.service;

import com.kripeshpoudel.entity.Comment;
import com.kripeshpoudel.entity.Post;
import com.kripeshpoudel.entity.User;
import com.kripeshpoudel.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification getNotificationById(Long notificationId);
    Notification getNotificationByReceiverAndOwningPostAndType(User receiver, Post owningPost, String type);
    void sendNotification(User receiver, User sender, Post owningPost, Comment owningComment, String type);
    void removeNotification(User receiver, Post owningPost, String type);
    List<Notification> getNotificationsForAuthUserPaginate(Integer page, Integer size);
    void markAllSeen();
    void markAllRead();
    void deleteNotification(User receiver, Post owningPost, String type);
    void deleteNotificationByOwningPost(Post owningPost);
    void deleteNotificationByOwningComment(Comment owningComment);
}
