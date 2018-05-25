package gcbenchmark;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class GCInfo {

    private List<Runnable> registration = new ArrayList<>();

    private void installGCMonitoring(){
        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();


        for (GarbageCollectorMXBean mxBean : mxBeans){
            NotificationEmitter emitter = (NotificationEmitter)mxBean;
            NotificationListener listener = (notification, handback)->{
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)){
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(
                            (CompositeData)notification.getUserData());
                    long duration = info.getGcInfo().getDuration();
                    String gcType = info.getGcAction();

                    System.out.println(gcType + ": - "
                            + info.getGcInfo().getId() + ", "
                            + info.getGcName()
                            + " (from " + info.getGcCause() + ") " + duration + " milliseconds");
                }
            };

            emitter.addNotificationListener(listener, null, null);

            registration.add(() -> {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
