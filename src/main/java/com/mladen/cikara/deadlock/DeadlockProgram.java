package com.mladen.cikara.deadlock;

public class DeadlockProgram {
  private static class DeadlockRunnable implements Runnable {
    private static void printLockedResource(Object resource) {
      System.out.println(Thread.currentThread().getName() + ": locked resource -> " + resource);
    }

    private final Object firstResource;

    private final Object secondResource;

    public DeadlockRunnable(Object firstResource, Object secondResource) {
      this.firstResource = firstResource;
      this.secondResource = secondResource;
    }

    @Override
    public void run() {
      try {
        synchronized (firstResource) {
          printLockedResource(firstResource);
          Thread.sleep(1000);
          synchronized (secondResource) {
            printLockedResource(secondResource);
          }
        }

      } catch (final InterruptedException e) {
        System.out.println("Exception occurred: " + e);
      }

    }

  }

  public static void main(String[] args) throws InterruptedException {
    final Object resourceA = new Object();
    final Object resourceB = new Object();

    final Thread threadLockingResourceAFirst =
        new Thread(new DeadlockRunnable(resourceA, resourceB));
    final Thread threadLockingResourceBFirst =
        new Thread(new DeadlockRunnable(resourceB, resourceA));

    threadLockingResourceAFirst.start();
    Thread.sleep(500);
    threadLockingResourceBFirst.start();
  }
}
