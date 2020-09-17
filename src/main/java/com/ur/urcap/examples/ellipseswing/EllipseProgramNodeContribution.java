package com.ur.urcap.examples.ellipseswing;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.UserInterfaceAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.feature.Feature;
import com.ur.urcap.api.domain.feature.FeatureModel;
import com.ur.urcap.api.domain.program.ProgramModel;
import com.ur.urcap.api.domain.program.nodes.ProgramNodeFactory;
import com.ur.urcap.api.domain.program.nodes.builtin.MoveNode;
import com.ur.urcap.api.domain.program.nodes.builtin.WaypointNode;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.MoveJMoveNodeConfig;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.MoveNodeConfig;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.MoveNodeConfigFactory;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.TCPSelection;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.TCPSelectionFactory;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.builder.MoveJConfigBuilder;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.builder.MoveNodeConfigBuilders;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.movenode.builder.MovePConfigBuilder;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.waypointnode.BlendParameters;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.waypointnode.WaypointMotionParameters;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.waypointnode.WaypointNodeConfig;
import com.ur.urcap.api.domain.program.nodes.builtin.configurations.waypointnode.WaypointNodeConfigFactory;
import com.ur.urcap.api.domain.program.structure.TreeNode;
import com.ur.urcap.api.domain.program.structure.TreeStructureException;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;
import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback2;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementCompleteEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementErrorEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovement;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovementCallback;
import com.ur.urcap.api.domain.validation.ErrorHandler;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.ValueFactoryProvider;
import com.ur.urcap.api.domain.value.blend.Blend;
import com.ur.urcap.api.domain.value.robotposition.PositionParameters;
import com.ur.urcap.api.domain.value.simple.Acceleration;
import com.ur.urcap.api.domain.value.simple.Angle;
import com.ur.urcap.api.domain.value.simple.Length;
import com.ur.urcap.api.domain.value.simple.SimpleValueFactory;
import com.ur.urcap.api.domain.value.simple.Speed;

import java.util.ArrayList;
import java.util.List;


public class EllipseProgramNodeContribution implements ProgramNodeContribution {

	private static final String DEFINED_KEY = "is_defined";
	private static final String PICKUP_POSITION = "pickup_pose";

	private static final double SHARED_TOOL_SPEED = 250;
	private static final double SHARED_TOOL_ACCELERATION = 1200;
	private static final double SHARED_BLEND_RADIUS_IN_MM = 23;
	private static final double HORIZONTAL_RADIUS_IN_MM = 200.0;
	private static final double VERTICAL_RADIUS_IN_MM = 120.0;
	private static final int NUMBER_OF_WAYPOINTS = 6;
	
	//dataModel VAR
	private static final String PICK_KEY = "pick";
	private static final int DEFAULT_PICK = 1;
	private static final String LEN_KEY = "len";
	private static final int DEFAULT_LEN = 0;
	private static final String DIAM_KEY = "diam";
	private static final int DEFAULT_DIAM = 0;
	
	// dataModel enable/disable  button
	
	private static final String P_KEY ="P";
	private static final boolean DEFAULT_P = false;
	private static final String P1_KEY ="P1";
	private static final boolean DEFAULT_P1 = false;
	private static final String P2_KEY ="P2";
	private static final boolean DEFAULT_P2 = false;
	// dataModel enable/disable label
	private static final String P_R_KEY ="P_R";
	private static final boolean DEFAULT_P_R = false;
	private static final String P1_R_KEY ="P1_R";
	private static final boolean DEFAULT_P1_R = false;
	private static final String P2_R_KEY ="P2_R";
	private static final boolean DEFAULT_P2_R = false;

	private final ProgramAPIProvider apiProvider;
	private final ProgramNodeFactory programNodeFactory;
	private final UndoRedoManager undoRedoManager;

	private MoveNode moveNode;

	private final List<WaypointNode> waypointNodes = new ArrayList<WaypointNode>();
	

	private DataModel dataModel;
	private TreeNode moveTreeNode;

	private WaypointNodeConfigFactory waypointNodeConfigFactory;

	private final EllipseProgramNodeView view;
	private final RobotMovement robotMovement;
	private EllipseState ellipseState = new EllipseState();
	//private int n = 0;

	public EllipseProgramNodeContribution(ProgramAPIProvider apiProvider, EllipseProgramNodeView view,
										  DataModel model) {
		this.apiProvider = apiProvider;
		this.dataModel = model;
		this.view = view;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();

		programNodeFactory = apiProvider.getProgramAPI().getProgramModel().getProgramNodeFactory();
		waypointNodeConfigFactory = programNodeFactory.createWaypointNode().getConfigFactory();
		robotMovement = apiProvider.getUserInterfaceAPI().getUserInteraction().getRobotMovement();
	}
	
	
	public void onPickValueChange(final int val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(PICK_KEY,val);
				
			}
		});
	}
	
	private int getPick() {
		return dataModel.get(PICK_KEY,DEFAULT_PICK);
	}
	
	public void onLenValueChange(final int val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(LEN_KEY,val);
				
			}
		});
	}
	
	private int getLen() {
		return dataModel.get(LEN_KEY,DEFAULT_LEN);
	}
	
	public void onDiamValueChange(final int val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(DIAM_KEY,val);
				
			}
		});
	}
	
	private int getDiam() {
		return dataModel.get(DIAM_KEY,DEFAULT_DIAM);
	}
	
	// Button state function 
	
	public void onPstateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(P_KEY,val);
				
			}
		});
	}
	
	private boolean getstateP() {
		return dataModel.get(P_KEY,DEFAULT_P);
	}
	
	public void onP1stateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(P1_KEY,val);
				
			}
		});
	}
	
	private boolean getstateP1() {
		return dataModel.get(P1_KEY,DEFAULT_P1);
	}
	
	
	public void onP2stateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(P2_KEY,val);
				
			}
		});
	}
	
	private boolean getstateP2() {
		return dataModel.get(P2_KEY,DEFAULT_P2);
	}
	
	

	@Override
	public void openView() {
		view.updateError(this.ellipseState.getMessage(), this.ellipseState.isError());
		//view.enableP2(dataModel.get(PICKUP_POSITION, (Pose) null) != null);
		view.enableP1(getstateP1());
		view.enableP2(getstateP2());
		view.LP1.setText("");
		view.LP2.setText("");
		view.LP3.setText("");
		view.text.setText("");
		//view.diamLabel.setText("0");
		//view.lenLabel.setText("0");
		
		view.setLenLabel(getLen());
		view.setDiamLabel(getDiam());
		view.setPickLabel(getPick());

		view.setLenSlider(getLen());
		view.setDiamSlider(getDiam());
		
	
		
	}

	@Override
	public void closeView() {
		// nothing needs to happen here in this example
	}

	@Override
	public String getTitle() {
		return "PickUp";
	}

	@Override
	public boolean isDefined() {
		return dataModel.get(DEFINED_KEY, false);
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		writer.writeChildren();
		writer.appendLine("global hbb_Diam = "+getDiam()+"");
		writer.appendLine("global hbb_Len = "+getLen()+"");
		writer.appendLine("global hbb_Pick = "+getPick()+"");
		//writer.appendLine("global P1 = "+dataModel.get(PICKUP_POSITION, 0)+"");
		
		
	}

	public void selectPickUpPoint(final int n, final int l) {
		clearErrors();
		UserInterfaceAPI uiapi = apiProvider.getUserInterfaceAPI();
		view.text.setText("");
		if(getDiam()==0 || getLen()==0) {
			view.text.setText("Set Leng and Diam");
			return;
			
		}
		
		uiapi.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback2() {
			@Override
			public void onOk(PositionParameters positionParameters) {
				
				
				if(n>0) {
					removeNodes();
					view.p1=0;
					view.p2=0;
					view.p3=0;
				}
				createNodes();
				configureMoveNode();
				adjustWaypointsToCenterPoint(positionParameters);
				
				//view.LP1.setText("READY");
				switch (l) {
				case 1:
					view.LP1.setText("READY");
					break;
				case 2:
					view.LP2.setText("READY");
					break;
				case 3:
					view.LP3.setText("READY");
					break;

				default:
					break;
				}
				
			}
		});
	}

	public void moveRobot() {
		clearErrors();
		Pose centerPose = dataModel.get(PICKUP_POSITION, (Pose) null);
		if (centerPose != null) {
			robotMovement.requestUserToMoveRobot(centerPose, new RobotMovementCallback() {

				@Override
				public void onComplete(MovementCompleteEvent event) {

				}

				@Override
				public void onError(MovementErrorEvent event) {
					updateError(new EllipseState(getErrorMessage(event.getErrorType())));
				}
			});
		}
	}

	private void updateError(EllipseState ellipseState) {
		this.ellipseState = ellipseState;
		view.updateError(ellipseState.getMessage(), ellipseState.isError());
	}

	private void clearErrors() {
		this.ellipseState = new EllipseState();
		view.clearErrors();
	}

	private String getErrorMessage(MovementErrorEvent.ErrorType errorType) {
		switch (errorType) {
			case UNREACHABLE_POSE:
				return "Could not move to  point.";

			default:
				return "Error in move here";
		}
	}

	private void removeNodes() {
		ProgramModel programModel = apiProvider.getProgramAPI().getProgramModel();
		TreeNode rootTreeNode = programModel.getRootTreeNode(this);
		try {
			for (TreeNode child : rootTreeNode.getChildren()) {
				rootTreeNode.removeChild(child);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void adjustWaypointsToCenterPoint(PositionParameters positionParameters) {
		dataModel.set(PICKUP_POSITION, positionParameters.getPose());
		try {
			configureWaypointNodes(positionParameters);
			setDefined(true);
		} catch (IllegalArgumentException e) {
			updateError(new EllipseState("Could not create ellipse movement<br>Try a different center point."));
			setDefined(false);
			resetWaypointNodes();
		}
		lockTreeNodes();
		//view.enableMoveButton(true);
	}

	private void resetWaypointNodes() {
		BlendParameters blendParameters = waypointNodeConfigFactory.createSharedBlendParameters();
		WaypointMotionParameters motionParameters = waypointNodeConfigFactory.createSharedMotionParameters();
		for (WaypointNode waypointNode : waypointNodes) {
			WaypointNodeConfig waypointNodeConfig = waypointNodeConfigFactory
					.createFixedPositionConfig(blendParameters, motionParameters);
			waypointNode.setConfig(waypointNodeConfig);
		}
	}

	private void configureWaypointNodes(PositionParameters positionParameters) {
		// adjust orientation according to base
		//double baseAngle = positionParameters.getJointPositions().getAllJointPositions()[0].getAngle(Angle.Unit.RAD) + (Math.PI / 2);


			int len = getLen();
			int dia = getDiam();
			int i =0;

		for (WaypointNode waypointNode : waypointNodes) {
		
			
			double offsetX = 0;
			double offsetY = 0;
			double offsetZ = 0;
			
			switch (i) {
			case 0:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = 1.5*len;
				break;
			case 1:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = 0;
				break;
			case 2:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = 0.15*len;
				break;
			case 3:
				 offsetX = 0;
				 offsetY = dia;
				 offsetZ = 0.15*len;
				break;
			case 4:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = 0.65*len;
				break;
			case 5:
				 offsetX = 0;
				 offsetY = -1.5*dia;
				 offsetZ = 0.65*len;
				break;


			default:
				break;
			};

			WaypointNodeConfig newWaypointNodeConfig = createWaypointConfig(positionParameters, offsetX, offsetY, offsetZ);
			waypointNode.setConfig(newWaypointNodeConfig);
			i++;
		}
	}

	private WaypointNodeConfig createWaypointConfig(PositionParameters positionParameters, double xOffsetInMM,
													double yOffsetInMM, double zOffsetInMM) {
		BlendParameters blendParameters = waypointNodeConfigFactory.createSharedBlendParameters();
		WaypointMotionParameters motionParameters = waypointNodeConfigFactory.createSharedMotionParameters();
		Pose pose = createPoseUsingCenterPoseAndOffset(positionParameters.getPose(), xOffsetInMM, yOffsetInMM, zOffsetInMM,
				Length.Unit.MM);
		

		return waypointNodeConfigFactory.createFixedPositionConfig(pose, positionParameters.getJointPositions(), positionParameters.getTCPOffset(),
				blendParameters, motionParameters);
	}

	private Pose createPoseUsingCenterPoseAndOffset(Pose pose, double xOffset, double yOffset, double zOffset,
													Length.Unit unit) {
		
		
		double x = pose.getPosition().getX(unit) + xOffset;
		double y = pose.getPosition().getY(unit) + yOffset;
		double z = pose.getPosition().getZ(unit) + zOffset;
		double rx = pose.getRotation().getRX(Angle.Unit.RAD);
		double ry = pose.getRotation().getRY(Angle.Unit.RAD);
		double rz = pose.getRotation().getRZ(Angle.Unit.RAD);
		ValueFactoryProvider valueFactoryProvider = apiProvider.getProgramAPI().getValueFactoryProvider();
		return valueFactoryProvider.getPoseFactory().createPose(x, y, z, rx, ry, rz, unit, Angle.Unit.RAD);
	}

	private void createNodes() {
		ProgramAPI programAPI = apiProvider.getProgramAPI();
		ProgramModel programModel = programAPI.getProgramModel();
		try {
			moveNode = programNodeFactory.createMoveNodeNoTemplate();
			TreeNode rootTreeNode = programModel.getRootTreeNode(this);
			moveTreeNode = rootTreeNode.addChild(moveNode);

			waypointNodes.clear();
			for (int i = 1; i <= NUMBER_OF_WAYPOINTS; i++) {
				createAndAddWaypointNode(i);
			}
		} catch (TreeStructureException e) {
			e.printStackTrace();
		}
	}

	private void configureMoveNode() {
		ProgramAPI programAPI = apiProvider.getProgramAPI();
		ValueFactoryProvider valueFactoryProvider = programAPI.getValueFactoryProvider();

		SimpleValueFactory valueFactory = valueFactoryProvider.getSimpleValueFactory();

		Speed speed = valueFactory.createSpeed(SHARED_TOOL_SPEED, Speed.Unit.MM_S);
		Acceleration acceleration = valueFactory.createAcceleration(SHARED_TOOL_ACCELERATION, Acceleration.Unit.MM_S2);
		Length length = valueFactory.createLength(SHARED_BLEND_RADIUS_IN_MM, Length.Unit.MM);
		Blend blend = valueFactoryProvider.getBlendFactory().createBlend(length);
		FeatureModel featureModel = programAPI.getFeatureModel();
		Feature feature = featureModel.getBaseFeature();
		TCPSelection tcpSelection = moveNode.getTCPSelectionFactory().createActiveTCPSelection();

		MovePConfigBuilder movePConfigBuilder = moveNode.getConfigBuilders().createMovePConfigBuilder()
				.setToolSpeed(speed, ErrorHandler.AUTO_CORRECT)
				.setToolAcceleration(acceleration, ErrorHandler.AUTO_CORRECT)
				.setBlend(blend, ErrorHandler.AUTO_CORRECT)
				.setFeature(feature)
				.setTCPSelection(tcpSelection);
				

		moveNode.setConfig(movePConfigBuilder.build());
		
		//// test MoveJ
		
		//MoveJConfigBuilder moveJConfigBuilder = moveNode.getConfigBuilders().createMoveJConfigBuilder();
		
	}

	private void createAndAddWaypointNode(int waypointNumber) throws TreeStructureException {
		String waypointName = createWaypointName(waypointNumber);
		WaypointNode waypointNode = programNodeFactory.createWaypointNode(waypointName);
		moveTreeNode.addChild(waypointNode);
		waypointNodes.add(waypointNode);
	}

	private static String createWaypointName(int waypointNumber) {
		switch (waypointNumber) {
		case 1:
			return "OverPart";
		case 2:
			return "PickPart";
		case 3:
			return "OverPickPart";
		case 4:
			return "TransitPoint1";
		case 5:
			return "TransitPoint2";
		case 6:
			return "LeavingFeeder";
			

		default:
			return "PickUpPoints" + waypointNumber;
		}
		
		///return "PickUpPoints" + waypointNumber;
	}

	private void lockTreeNodes() {
		ProgramAPI programAPI = apiProvider.getProgramAPI();
		ProgramModel programModel = programAPI.getProgramModel();
		TreeNode thisTreeNode = programModel.getRootTreeNode(this);
		thisTreeNode.setChildSequenceLocked(true);
		moveTreeNode.setChildSequenceLocked(true);
	}

	private void setDefined(boolean defined) {
		dataModel.set(DEFINED_KEY, defined);
	}

	private static class EllipseState {
		private final String message;
		private final boolean isError;

		EllipseState() {
			this.isError = false;
			this.message = "";
		}

		EllipseState(String message) {
			this.isError = true;
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public boolean isError() {
			return isError;
		}
	}
}
