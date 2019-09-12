/** Generated by YAKINDU Statechart Tools code generator. */
package traffic.light.trafficlightctrl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import traffic.light.ITimer;

public class TrafficLightCtrlStatemachine implements ITrafficLightCtrlStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean police_interrupt;
		
		
		public synchronized void raisePolice_interrupt() {
			police_interrupt = true;
			runCycle();
		}
		
		private boolean toggle;
		
		
		public synchronized void raiseToggle() {
			toggle = true;
			runCycle();
		}
		
		private long redPeriod;
		
		public synchronized long getRedPeriod() {
			return redPeriod;
		}
		
		public synchronized void setRedPeriod(long value) {
			this.redPeriod = value;
		}
		
		private long greenPeriod;
		
		public synchronized long getGreenPeriod() {
			return greenPeriod;
		}
		
		public synchronized void setGreenPeriod(long value) {
			this.greenPeriod = value;
		}
		
		private long yellowPeriod;
		
		public synchronized long getYellowPeriod() {
			return yellowPeriod;
		}
		
		public synchronized void setYellowPeriod(long value) {
			this.yellowPeriod = value;
		}
		
		protected void clearEvents() {
			police_interrupt = false;
			toggle = false;
		}
	}
	
	
	protected class SCITrafficLightImpl implements SCITrafficLight {
	
		private List<SCITrafficLightListener> listeners = new LinkedList<SCITrafficLightListener>();
		
		public List<SCITrafficLightListener> getListeners() {
			return listeners;
		}
		private boolean displayRed;
		
		
		public synchronized boolean isRaisedDisplayRed() {
			return displayRed;
		}
		
		protected void raiseDisplayRed() {
			displayRed = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayRedRaised();
			}
		}
		
		private boolean displayGreen;
		
		
		public synchronized boolean isRaisedDisplayGreen() {
			return displayGreen;
		}
		
		protected void raiseDisplayGreen() {
			displayGreen = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayGreenRaised();
			}
		}
		
		private boolean displayYellow;
		
		
		public synchronized boolean isRaisedDisplayYellow() {
			return displayYellow;
		}
		
		protected void raiseDisplayYellow() {
			displayYellow = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayYellowRaised();
			}
		}
		
		private boolean displayNone;
		
		
		public synchronized boolean isRaisedDisplayNone() {
			return displayNone;
		}
		
		protected void raiseDisplayNone() {
			displayNone = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayNoneRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		displayRed = false;
		displayGreen = false;
		displayYellow = false;
		displayNone = false;
		}
		
	}
	
	
	protected class SCITimerImpl implements SCITimer {
	
		private List<SCITimerListener> listeners = new LinkedList<SCITimerListener>();
		
		public List<SCITimerListener> getListeners() {
			return listeners;
		}
		private boolean updateTimerColour;
		
		private String updateTimerColourValue;
		
		
		public synchronized boolean isRaisedUpdateTimerColour() {
			return updateTimerColour;
		}
		
		protected synchronized void raiseUpdateTimerColour(String value) {
			updateTimerColourValue = value;
			updateTimerColour = true;
			for (SCITimerListener listener : listeners) {
				listener.onUpdateTimerColourRaised(value);
			}
		}
		
		public synchronized String getUpdateTimerColourValue() {
			if (! updateTimerColour ) 
				throw new IllegalStateException("Illegal event value access. Event UpdateTimerColour is not raised!");
			return updateTimerColourValue;
		}
		
		private boolean updateTimerValue;
		
		private long updateTimerValueValue;
		
		
		public synchronized boolean isRaisedUpdateTimerValue() {
			return updateTimerValue;
		}
		
		protected synchronized void raiseUpdateTimerValue(long value) {
			updateTimerValueValue = value;
			updateTimerValue = true;
			for (SCITimerListener listener : listeners) {
				listener.onUpdateTimerValueRaised(value);
			}
		}
		
		public synchronized long getUpdateTimerValueValue() {
			if (! updateTimerValue ) 
				throw new IllegalStateException("Illegal event value access. Event UpdateTimerValue is not raised!");
			return updateTimerValueValue;
		}
		
		public synchronized long getOFF() {
			return oFF;
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		updateTimerColour = false;
		updateTimerValue = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	protected SCITrafficLightImpl sCITrafficLight;
	
	protected SCITimerImpl sCITimer;
	
	private boolean initialized = false;
	
	public enum State {
		main_main,
		main_main_trafficlight_interrupted,
		main_main_trafficlight_interrupted_blinking_Black,
		main_main_trafficlight_interrupted_blinking_Yellow,
		main_main_trafficlight_normal,
		main_main_trafficlight_normal_normal_Red,
		main_main_trafficlight_normal_normal_Yellow,
		main_main_trafficlight_normal_normal_Green,
		main_main_timer_disabled,
		main_main_timer_running,
		main_main_timer_running_running_Green,
		main_main_timer_running_running_Red,
		main_off,
		$NullState$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[2];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[7];
	
	private Queue<Runnable> internalEventQueue = new LinkedList<Runnable>();
	private boolean resetTimer;
	private boolean disableTimer;
	private boolean enableTimer;
	private long counter;
	
	protected long getCounter() {
		return counter;
	}
	
	protected void setCounter(long value) {
		this.counter = value;
	}
	
	
	public TrafficLightCtrlStatemachine() {
		sCInterface = new SCInterfaceImpl();
		sCITrafficLight = new SCITrafficLightImpl();
		sCITimer = new SCITimerImpl();
	}
	
	public synchronized void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NullState$;
		}
		for (int i = 0; i < 1; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setRedPeriod(60);
		
		sCInterface.setGreenPeriod(55);
		
		sCInterface.setYellowPeriod(5);
		
		setCounter(0);
	}
	
	public synchronized void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_main_default();
	}
	
	public synchronized void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		
		clearOutEvents();
	
		Runnable task = getNextEvent();
		if (task == null) {
			task = getDefaultEvent();
		}
		
		while (task != null) {
			task.run();
			clearEvents();
			task = getNextEvent();
		}
		
	}
	
	protected synchronized void singleCycle() {
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
				case main_main_trafficlight_interrupted_blinking_Black:
					main_main_trafficlight_interrupted_blinking_Black_react(true);
					break;
				case main_main_trafficlight_interrupted_blinking_Yellow:
					main_main_trafficlight_interrupted_blinking_Yellow_react(true);
					break;
				case main_main_trafficlight_normal_normal_Red:
					main_main_trafficlight_normal_normal_Red_react(true);
					break;
				case main_main_trafficlight_normal_normal_Yellow:
					main_main_trafficlight_normal_normal_Yellow_react(true);
					break;
				case main_main_trafficlight_normal_normal_Green:
					main_main_trafficlight_normal_normal_Green_react(true);
					break;
				case main_main_timer_disabled:
					main_main_timer_disabled_react(true);
					break;
				case main_main_timer_running_running_Green:
					main_main_timer_running_running_Green_react(true);
					break;
				case main_main_timer_running_running_Red:
					main_main_timer_running_running_Red_react(true);
					break;
				case main_off:
					main_off_react(true);
					break;
			default:
				// $NullState$
			}
		}
	}
	
	protected Runnable getNextEvent() {
		if(!internalEventQueue.isEmpty()) {
			return internalEventQueue.poll();
		}
		return null;
	}
	
	protected Runnable getDefaultEvent() {
		return new Runnable() {
			@Override
			public void run() {
				singleCycle();
			}
		};
	}
	
	public synchronized void exit() {
		exitSequence_main();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
		sCITrafficLight.clearEvents();
		sCITimer.clearEvents();
		resetTimer = false;
		disableTimer = false;
		enableTimer = false;
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCITrafficLight.clearOutEvents();
		sCITimer.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case main_main:
			return stateVector[0].ordinal() >= State.
					main_main.ordinal()&& stateVector[0].ordinal() <= State.main_main_timer_running_running_Red.ordinal();
		case main_main_trafficlight_interrupted:
			return stateVector[0].ordinal() >= State.
					main_main_trafficlight_interrupted.ordinal()&& stateVector[0].ordinal() <= State.main_main_trafficlight_interrupted_blinking_Yellow.ordinal();
		case main_main_trafficlight_interrupted_blinking_Black:
			return stateVector[0] == State.main_main_trafficlight_interrupted_blinking_Black;
		case main_main_trafficlight_interrupted_blinking_Yellow:
			return stateVector[0] == State.main_main_trafficlight_interrupted_blinking_Yellow;
		case main_main_trafficlight_normal:
			return stateVector[0].ordinal() >= State.
					main_main_trafficlight_normal.ordinal()&& stateVector[0].ordinal() <= State.main_main_trafficlight_normal_normal_Green.ordinal();
		case main_main_trafficlight_normal_normal_Red:
			return stateVector[0] == State.main_main_trafficlight_normal_normal_Red;
		case main_main_trafficlight_normal_normal_Yellow:
			return stateVector[0] == State.main_main_trafficlight_normal_normal_Yellow;
		case main_main_trafficlight_normal_normal_Green:
			return stateVector[0] == State.main_main_trafficlight_normal_normal_Green;
		case main_main_timer_disabled:
			return stateVector[1] == State.main_main_timer_disabled;
		case main_main_timer_running:
			return stateVector[1].ordinal() >= State.
					main_main_timer_running.ordinal()&& stateVector[1].ordinal() <= State.main_main_timer_running_running_Red.ordinal();
		case main_main_timer_running_running_Green:
			return stateVector[1] == State.main_main_timer_running_running_Green;
		case main_main_timer_running_running_Red:
			return stateVector[1] == State.main_main_timer_running_running_Red;
		case main_off:
			return stateVector[0] == State.main_off;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correctly
	* executed.
	* 
	* @param timer
	*/
	public synchronized void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public synchronized void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
		runCycle();
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public SCITrafficLight getSCITrafficLight() {
		return sCITrafficLight;
	}
	
	public SCITimer getSCITimer() {
		return sCITimer;
	}
	
	private void raiseResetTimer() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				resetTimer = true;					
				singleCycle();
			}
		});
	}
	
	private void raiseDisableTimer() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				disableTimer = true;					
				singleCycle();
			}
		});
	}
	
	private void raiseEnableTimer() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				enableTimer = true;					
				singleCycle();
			}
		});
	}
	
	public synchronized void raisePolice_interrupt() {
		sCInterface.raisePolice_interrupt();
	}
	
	public synchronized void raiseToggle() {
		sCInterface.raiseToggle();
	}
	
	public synchronized long getRedPeriod() {
		return sCInterface.getRedPeriod();
	}
	
	public synchronized void setRedPeriod(long value) {
		sCInterface.setRedPeriod(value);
	}
	
	public synchronized long getGreenPeriod() {
		return sCInterface.getGreenPeriod();
	}
	
	public synchronized void setGreenPeriod(long value) {
		sCInterface.setGreenPeriod(value);
	}
	
	public synchronized long getYellowPeriod() {
		return sCInterface.getYellowPeriod();
	}
	
	public synchronized void setYellowPeriod(long value) {
		sCInterface.setYellowPeriod(value);
	}
	
	private boolean check_main_main_timer_running_running__choice_0_tr0_tr0() {
		return isStateActive(State.main_main_trafficlight_normal_normal_Green);
	}
	
	private void effect_main_main_timer_running_running__choice_0_tr0() {
		sCITimer.raiseUpdateTimerColour("Green");
		
		enterSequence_main_main_timer_running_running_Green_default();
	}
	
	private void effect_main_main_timer_running_running__choice_0_tr1() {
		sCITimer.raiseUpdateTimerColour("Red");
		
		enterSequence_main_main_timer_running_running_Red_default();
	}
	
	/* Entry action for state 'Black'. */
	private void entryAction_main_main_trafficlight_interrupted_blinking_Black() {
		timer.setTimer(this, 0, 500, false);
		
		sCITrafficLight.raiseDisplayNone();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_main_trafficlight_interrupted_blinking_Yellow() {
		timer.setTimer(this, 1, 500, false);
		
		sCITrafficLight.raiseDisplayYellow();
	}
	
	/* Entry action for state 'Red'. */
	private void entryAction_main_main_trafficlight_normal_normal_Red() {
		timer.setTimer(this, 2, (sCInterface.getRedPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayRed();
		
		setCounter(sCInterface.redPeriod);
		
		raiseResetTimer();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_main_trafficlight_normal_normal_Yellow() {
		timer.setTimer(this, 3, (sCInterface.getYellowPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayYellow();
		
		raiseDisableTimer();
	}
	
	/* Entry action for state 'Green'. */
	private void entryAction_main_main_trafficlight_normal_normal_Green() {
		timer.setTimer(this, 4, (sCInterface.getGreenPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayGreen();
		
		setCounter(sCInterface.greenPeriod);
		
		raiseResetTimer();
	}
	
	/* Entry action for state 'Green'. */
	private void entryAction_main_main_timer_running_running_Green() {
		timer.setTimer(this, 5, (1 * 1000), false);
		
		sCITimer.raiseUpdateTimerValue(getCounter());
	}
	
	/* Entry action for state 'Red'. */
	private void entryAction_main_main_timer_running_running_Red() {
		timer.setTimer(this, 6, (1 * 1000), false);
		
		sCITimer.raiseUpdateTimerValue(getCounter());
	}
	
	/* Exit action for state 'main'. */
	private void exitAction_main_main() {
		sCITrafficLight.raiseDisplayNone();
	}
	
	/* Exit action for state 'Black'. */
	private void exitAction_main_main_trafficlight_interrupted_blinking_Black() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'Yellow'. */
	private void exitAction_main_main_trafficlight_interrupted_blinking_Yellow() {
		timer.unsetTimer(this, 1);
	}
	
	/* Exit action for state 'Red'. */
	private void exitAction_main_main_trafficlight_normal_normal_Red() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'Yellow'. */
	private void exitAction_main_main_trafficlight_normal_normal_Yellow() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Green'. */
	private void exitAction_main_main_trafficlight_normal_normal_Green() {
		timer.unsetTimer(this, 4);
	}
	
	/* Exit action for state 'running'. */
	private void exitAction_main_main_timer_running() {
		sCITimer.raiseUpdateTimerValue(sCITimer.getOFF());
	}
	
	/* Exit action for state 'Green'. */
	private void exitAction_main_main_timer_running_running_Green() {
		timer.unsetTimer(this, 5);
	}
	
	/* Exit action for state 'Red'. */
	private void exitAction_main_main_timer_running_running_Red() {
		timer.unsetTimer(this, 6);
	}
	
	/* 'default' enter sequence for state main */
	private void enterSequence_main_main_default() {
		enterSequence_main_main_trafficlight_default();
		enterSequence_main_main_timer_default();
	}
	
	/* 'default' enter sequence for state interrupted */
	private void enterSequence_main_main_trafficlight_interrupted_default() {
		enterSequence_main_main_trafficlight_interrupted_blinking_default();
	}
	
	/* 'default' enter sequence for state Black */
	private void enterSequence_main_main_trafficlight_interrupted_blinking_Black_default() {
		entryAction_main_main_trafficlight_interrupted_blinking_Black();
		nextStateIndex = 0;
		stateVector[0] = State.main_main_trafficlight_interrupted_blinking_Black;
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_main_trafficlight_interrupted_blinking_Yellow_default() {
		entryAction_main_main_trafficlight_interrupted_blinking_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_main_trafficlight_interrupted_blinking_Yellow;
	}
	
	/* 'default' enter sequence for state normal */
	private void enterSequence_main_main_trafficlight_normal_default() {
		enterSequence_main_main_trafficlight_normal_normal_default();
	}
	
	/* 'default' enter sequence for state Red */
	private void enterSequence_main_main_trafficlight_normal_normal_Red_default() {
		entryAction_main_main_trafficlight_normal_normal_Red();
		nextStateIndex = 0;
		stateVector[0] = State.main_main_trafficlight_normal_normal_Red;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_main_trafficlight_normal_normal_Yellow_default() {
		entryAction_main_main_trafficlight_normal_normal_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_main_trafficlight_normal_normal_Yellow;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Green */
	private void enterSequence_main_main_trafficlight_normal_normal_Green_default() {
		entryAction_main_main_trafficlight_normal_normal_Green();
		nextStateIndex = 0;
		stateVector[0] = State.main_main_trafficlight_normal_normal_Green;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state disabled */
	private void enterSequence_main_main_timer_disabled_default() {
		nextStateIndex = 1;
		stateVector[1] = State.main_main_timer_disabled;
	}
	
	/* 'default' enter sequence for state running */
	private void enterSequence_main_main_timer_running_default() {
		enterSequence_main_main_timer_running_running_default();
	}
	
	/* 'default' enter sequence for state Green */
	private void enterSequence_main_main_timer_running_running_Green_default() {
		entryAction_main_main_timer_running_running_Green();
		nextStateIndex = 1;
		stateVector[1] = State.main_main_timer_running_running_Green;
	}
	
	/* 'default' enter sequence for state Red */
	private void enterSequence_main_main_timer_running_running_Red_default() {
		entryAction_main_main_timer_running_running_Red();
		nextStateIndex = 1;
		stateVector[1] = State.main_main_timer_running_running_Red;
	}
	
	/* 'default' enter sequence for state off */
	private void enterSequence_main_off_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_off;
	}
	
	/* 'default' enter sequence for region main */
	private void enterSequence_main_default() {
		react_main__entry_Default();
	}
	
	/* 'default' enter sequence for region trafficlight */
	private void enterSequence_main_main_trafficlight_default() {
		react_main_main_trafficlight__entry_Default();
	}
	
	/* 'default' enter sequence for region blinking */
	private void enterSequence_main_main_trafficlight_interrupted_blinking_default() {
		react_main_main_trafficlight_interrupted_blinking__entry_Default();
	}
	
	/* 'default' enter sequence for region normal */
	private void enterSequence_main_main_trafficlight_normal_normal_default() {
		react_main_main_trafficlight_normal_normal__entry_Default();
	}
	
	/* deep enterSequence with history in child normal */
	private void deepEnterSequence_main_main_trafficlight_normal_normal() {
		switch (historyVector[0]) {
		case main_main_trafficlight_normal_normal_Red:
			enterSequence_main_main_trafficlight_normal_normal_Red_default();
			break;
		case main_main_trafficlight_normal_normal_Yellow:
			enterSequence_main_main_trafficlight_normal_normal_Yellow_default();
			break;
		case main_main_trafficlight_normal_normal_Green:
			enterSequence_main_main_trafficlight_normal_normal_Green_default();
			break;
		default:
			break;
		}
	}
	
	/* 'default' enter sequence for region timer */
	private void enterSequence_main_main_timer_default() {
		react_main_main_timer__entry_Default();
	}
	
	/* 'default' enter sequence for region running */
	private void enterSequence_main_main_timer_running_running_default() {
		react_main_main_timer_running_running__entry_Default();
	}
	
	/* Default exit sequence for state main */
	private void exitSequence_main_main() {
		exitSequence_main_main_trafficlight();
		exitSequence_main_main_timer();
		exitAction_main_main();
	}
	
	/* Default exit sequence for state interrupted */
	private void exitSequence_main_main_trafficlight_interrupted() {
		exitSequence_main_main_trafficlight_interrupted_blinking();
	}
	
	/* Default exit sequence for state Black */
	private void exitSequence_main_main_trafficlight_interrupted_blinking_Black() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_main_trafficlight_interrupted_blinking_Black();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_main_trafficlight_interrupted_blinking_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_main_trafficlight_interrupted_blinking_Yellow();
	}
	
	/* Default exit sequence for state normal */
	private void exitSequence_main_main_trafficlight_normal() {
		exitSequence_main_main_trafficlight_normal_normal();
	}
	
	/* Default exit sequence for state Red */
	private void exitSequence_main_main_trafficlight_normal_normal_Red() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_main_trafficlight_normal_normal_Red();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_main_trafficlight_normal_normal_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_main_trafficlight_normal_normal_Yellow();
	}
	
	/* Default exit sequence for state Green */
	private void exitSequence_main_main_trafficlight_normal_normal_Green() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_main_trafficlight_normal_normal_Green();
	}
	
	/* Default exit sequence for state disabled */
	private void exitSequence_main_main_timer_disabled() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state running */
	private void exitSequence_main_main_timer_running() {
		exitSequence_main_main_timer_running_running();
		exitAction_main_main_timer_running();
	}
	
	/* Default exit sequence for state Green */
	private void exitSequence_main_main_timer_running_running_Green() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
		
		exitAction_main_main_timer_running_running_Green();
	}
	
	/* Default exit sequence for state Red */
	private void exitSequence_main_main_timer_running_running_Red() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
		
		exitAction_main_main_timer_running_running_Red();
	}
	
	/* Default exit sequence for state off */
	private void exitSequence_main_off() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main */
	private void exitSequence_main() {
		switch (stateVector[0]) {
		case main_main_trafficlight_interrupted_blinking_Black:
			exitSequence_main_main_trafficlight_interrupted_blinking_Black();
			break;
		case main_main_trafficlight_interrupted_blinking_Yellow:
			exitSequence_main_main_trafficlight_interrupted_blinking_Yellow();
			break;
		case main_main_trafficlight_normal_normal_Red:
			exitSequence_main_main_trafficlight_normal_normal_Red();
			break;
		case main_main_trafficlight_normal_normal_Yellow:
			exitSequence_main_main_trafficlight_normal_normal_Yellow();
			break;
		case main_main_trafficlight_normal_normal_Green:
			exitSequence_main_main_trafficlight_normal_normal_Green();
			break;
		case main_off:
			exitSequence_main_off();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case main_main_timer_disabled:
			exitSequence_main_main_timer_disabled();
			exitAction_main_main();
			break;
		case main_main_timer_running_running_Green:
			exitSequence_main_main_timer_running_running_Green();
			exitAction_main_main_timer_running();
			exitAction_main_main();
			break;
		case main_main_timer_running_running_Red:
			exitSequence_main_main_timer_running_running_Red();
			exitAction_main_main_timer_running();
			exitAction_main_main();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region trafficlight */
	private void exitSequence_main_main_trafficlight() {
		switch (stateVector[0]) {
		case main_main_trafficlight_interrupted_blinking_Black:
			exitSequence_main_main_trafficlight_interrupted_blinking_Black();
			break;
		case main_main_trafficlight_interrupted_blinking_Yellow:
			exitSequence_main_main_trafficlight_interrupted_blinking_Yellow();
			break;
		case main_main_trafficlight_normal_normal_Red:
			exitSequence_main_main_trafficlight_normal_normal_Red();
			break;
		case main_main_trafficlight_normal_normal_Yellow:
			exitSequence_main_main_trafficlight_normal_normal_Yellow();
			break;
		case main_main_trafficlight_normal_normal_Green:
			exitSequence_main_main_trafficlight_normal_normal_Green();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region blinking */
	private void exitSequence_main_main_trafficlight_interrupted_blinking() {
		switch (stateVector[0]) {
		case main_main_trafficlight_interrupted_blinking_Black:
			exitSequence_main_main_trafficlight_interrupted_blinking_Black();
			break;
		case main_main_trafficlight_interrupted_blinking_Yellow:
			exitSequence_main_main_trafficlight_interrupted_blinking_Yellow();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region normal */
	private void exitSequence_main_main_trafficlight_normal_normal() {
		switch (stateVector[0]) {
		case main_main_trafficlight_normal_normal_Red:
			exitSequence_main_main_trafficlight_normal_normal_Red();
			break;
		case main_main_trafficlight_normal_normal_Yellow:
			exitSequence_main_main_trafficlight_normal_normal_Yellow();
			break;
		case main_main_trafficlight_normal_normal_Green:
			exitSequence_main_main_trafficlight_normal_normal_Green();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region timer */
	private void exitSequence_main_main_timer() {
		switch (stateVector[1]) {
		case main_main_timer_disabled:
			exitSequence_main_main_timer_disabled();
			break;
		case main_main_timer_running_running_Green:
			exitSequence_main_main_timer_running_running_Green();
			exitAction_main_main_timer_running();
			break;
		case main_main_timer_running_running_Red:
			exitSequence_main_main_timer_running_running_Red();
			exitAction_main_main_timer_running();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region running */
	private void exitSequence_main_main_timer_running_running() {
		switch (stateVector[1]) {
		case main_main_timer_running_running_Green:
			exitSequence_main_main_timer_running_running_Green();
			break;
		case main_main_timer_running_running_Red:
			exitSequence_main_main_timer_running_running_Red();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state null. */
	private void react_main_main_timer_running_running__choice_0() {
		if (check_main_main_timer_running_running__choice_0_tr0_tr0()) {
			effect_main_main_timer_running_running__choice_0_tr0();
		} else {
			effect_main_main_timer_running_running__choice_0_tr1();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_main_trafficlight_interrupted_blinking__entry_Default() {
		enterSequence_main_main_trafficlight_interrupted_blinking_Yellow_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_main_trafficlight_normal_normal__entry_Default() {
		enterSequence_main_main_trafficlight_normal_normal_Red_default();
	}
	
	/* Default react sequence for deep history entry hist */
	private void react_main_main_trafficlight_normal_normal_hist() {
		/* Enter the region with deep history */
		if (historyVector[0] != State.$NullState$) {
			deepEnterSequence_main_main_trafficlight_normal_normal();
		} else {
			enterSequence_main_main_trafficlight_normal_normal_Red_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_main_trafficlight__entry_Default() {
		enterSequence_main_main_trafficlight_normal_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_main_timer_running_running__entry_Default() {
		react_main_main_timer_running_running__choice_0();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_main_timer__entry_Default() {
		enterSequence_main_main_timer_running_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main__entry_Default() {
		enterSequence_main_off_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_main_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.toggle) {
				exitSequence_main_main();
				enterSequence_main_off_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_interrupted_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.police_interrupt) {
				exitSequence_main_main_trafficlight_interrupted();
				raiseEnableTimer();
				
				react_main_main_trafficlight_normal_normal_hist();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_interrupted_blinking_Black_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[0]) {
				exitSequence_main_main_trafficlight_interrupted_blinking_Black();
				enterSequence_main_main_trafficlight_interrupted_blinking_Yellow_default();
				main_main_trafficlight_interrupted_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_trafficlight_interrupted_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_interrupted_blinking_Yellow_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[1]) {
				exitSequence_main_main_trafficlight_interrupted_blinking_Yellow();
				enterSequence_main_main_trafficlight_interrupted_blinking_Black_default();
				main_main_trafficlight_interrupted_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_trafficlight_interrupted_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_normal_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.police_interrupt) {
				exitSequence_main_main_trafficlight_normal();
				raiseDisableTimer();
				
				enterSequence_main_main_trafficlight_interrupted_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_normal_normal_Red_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[2]) {
				exitSequence_main_main_trafficlight_normal_normal_Red();
				enterSequence_main_main_trafficlight_normal_normal_Green_default();
				main_main_trafficlight_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_trafficlight_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_normal_normal_Yellow_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[3]) {
				exitSequence_main_main_trafficlight_normal_normal_Yellow();
				raiseEnableTimer();
				
				enterSequence_main_main_trafficlight_normal_normal_Red_default();
				main_main_trafficlight_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_trafficlight_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_trafficlight_normal_normal_Green_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[4]) {
				exitSequence_main_main_trafficlight_normal_normal_Green();
				enterSequence_main_main_trafficlight_normal_normal_Yellow_default();
				main_main_trafficlight_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_trafficlight_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_timer_disabled_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (enableTimer) {
				exitSequence_main_main_timer_disabled();
				enterSequence_main_main_timer_running_default();
				main_main_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_timer_running_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (disableTimer) {
				exitSequence_main_main_timer_running();
				enterSequence_main_main_timer_disabled_default();
				main_main_react(false);
			} else {
				if (resetTimer) {
					exitSequence_main_main_timer_running();
					enterSequence_main_main_timer_running_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			did_transition = main_main_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_timer_running_running_Green_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[5]) {
				exitSequence_main_main_timer_running_running_Green();
				setCounter(getCounter() - 1);
				
				enterSequence_main_main_timer_running_running_Green_default();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_timer_running_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_main_timer_running_running_Red_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[6]) {
				exitSequence_main_main_timer_running_running_Red();
				setCounter(getCounter() - 1);
				
				enterSequence_main_main_timer_running_running_Red_default();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_main_timer_running_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_off_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.toggle) {
				exitSequence_main_off();
				enterSequence_main_main_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
}