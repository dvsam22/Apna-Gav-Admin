const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendVillageNotification = functions.firestore
    .document('villages/{villageId}/notifications/{notificationId}')
    .onCreate(async (snapshot, context) => {
        const notificationData = snapshot.data();
        const villageId = context.params.villageId;

        // Notification ka content
        const payload = {
            notification: {
                title: notificationData.title,
                body: notificationData.message,
                sound: "default"
            },
            // Extra data agar app me handle karna ho
            data: {
                villageId: villageId,
                id: context.params.notificationId,
                type: "notice",
                click_action: "FLUTTER_NOTIFICATION_CLICK" // Ya jo bhi aapki activity ho
            }
        };

        // Jis topic par bhejni hai (Village ID ke base par)
        const topic = `village_${villageId}`;

        try {
            const response = await admin.messaging().sendToTopic(topic, payload);
            console.log('Successfully sent message to topic:', topic, response);
            return null;
        } catch (error) {
            console.log('Error sending message:', error);
            return null;
        }
    });