package pansong291.xposed.quickenergy.ui;

import java.util.*;
import pansong291.xposed.quickenergy.util.FriendIdMap;

public class AlipayUser extends AlipayId {
  private static List<AlipayUser> list;

  public AlipayUser(String i, String n) {
    id = i;
    name = n;
  }

  public static List<AlipayUser> getList() {
    if (list == null || FriendIdMap.shouldReload) {
      list = new ArrayList<AlipayUser>();
      Set idSet = FriendIdMap.getIdMap().entrySet();
      for (Object item : idSet) {
        Map.Entry entry = (Map.Entry) item;
        list.add(new AlipayUser(entry.getKey().toString(), entry.getValue().toString()));
      }
    }
    return list;
  }

  public static void remove(String id) {
    getList();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).id.equals(id)) {
        list.remove(i);
        break;
      }
    }
  }
}
