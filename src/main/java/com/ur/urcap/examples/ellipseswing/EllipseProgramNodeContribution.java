package com.ur.urcap.examples.ellipseswing;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.UserInterfaceAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;
import com.ur.urcap.api.domain.userinteraction.RobotPositionCallback2;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementCompleteEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementErrorEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovement;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovementCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.ValueFactoryProvider;
import com.ur.urcap.api.domain.value.expression.Expression;
import com.ur.urcap.api.domain.value.expression.ExpressionBuilder;
import com.ur.urcap.api.domain.value.expression.InvalidExpressionException;
import com.ur.urcap.api.domain.value.robotposition.PositionParameters;
import com.ur.urcap.api.domain.value.simple.Angle;
import com.ur.urcap.api.domain.value.simple.Length;
import com.ur.urcap.api.domain.variable.GlobalVariable;
import com.ur.urcap.api.domain.variable.VariableException;
import com.ur.urcap.api.domain.variable.VariableFactory;
import java.util.ArrayList;
import java.util.List;

public class EllipseProgramNodeContribution implements ProgramNodeContribution {

	private static final String DEFINED_KEY = "is_defined";
	private static final boolean DEFAULT_DEFINE = false;
	private static final String SET1_KEY = "Set1";
	private static final boolean DEFAULT_SET1 = true;
	private static final String SET2_KEY = "Set2";
	private static final boolean DEFAULT_SET2 = false;
	
	private static final String PICKUP_POSITION = "pickup_pose";

	//dataModel VAR
	private static final String PICK_KEY = "pick";
	private static final int DEFAULT_PICK = 0;
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
	private static final String PM_KEY="PM";
	private static final boolean DEFAULT_PM = false;
	
	// dataModel enable/disable label
	private static final String P_R_KEY ="P_R";
	private static final String DEFAULT_P_R = "";
	private static final String P1_R_KEY ="P1_R";
	private static final String DEFAULT_P1_R = "";
	private static final String P2_R_KEY ="P2_R";
	private static final String DEFAULT_P2_R = "";
	
	// var to implement  clearNode
	public int p1; 
	public int p2;
	public int p3;
	
	public int f =0;
	public String text="";
	private  String seria = "20195000394";
	//private  String seria = "20195599999";

	private final ProgramAPIProvider apiProvider;
	private final UndoRedoManager undoRedoManager;

	public final List<Pose> poses = new ArrayList<Pose>();
	
	private DataModel dataModel;

	private final EllipseProgramNodeView view;
	private final RobotMovement robotMovement;
	private EllipseState ellipseState = new EllipseState();
	
	public EllipseProgramNodeContribution(ProgramAPIProvider apiProvider, EllipseProgramNodeView view,
										  DataModel model) {
		this.apiProvider = apiProvider;
		this.dataModel = model;
		this.view = view;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();

		robotMovement = apiProvider.getUserInterfaceAPI().getUserInteraction().getRobotMovement();
	}
	
	
	public String getSeria() {
		String seria = apiProvider.getSystemAPI().getRobotModel().getSerialNumber();
		System.out.println("SERIA"+seria);
		return seria;
	}
	
	// set and get data from DataModel 
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
	
	// Button state function in dataModel
	
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
	
	
	public void onPMstateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(PM_KEY,val);
				
			}
		});
	}
	
	private boolean getstatePM() {
		return dataModel.get(PM_KEY,DEFAULT_PM);
	}
	
	
	// Set Button state 
	public void onSet1stateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(SET1_KEY,val);
				
			}
		});
	}
	
	private boolean getstateSet1() {
		return dataModel.get(SET1_KEY,DEFAULT_SET1);
	}
	
	
	public void onSet2stateChange(final boolean val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(SET2_KEY,val);
				
			}
		});
	}
	
	private boolean getstateSet2() {
		return dataModel.get(SET2_KEY,DEFAULT_SET2);
	}
	
	// isDefine dataModel
	public void onDefinestateChange(final boolean val) {
		System.out.println("Hello from onDefineChange ProgramNode !!!!!!!!!!!!!!!!!!!!!");
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(DEFINED_KEY,val);
				
			}
		});
	}
	
	private boolean getstateDefine() {
		return dataModel.get(DEFINED_KEY,DEFAULT_DEFINE);
	}
	
	// Button Label state function in dataModel
	
	public void onPRstateChange(final String val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
		
			@Override
			public void executeChanges() {
				dataModel.set(P_R_KEY,val);
				
			}
		});
	}
	
	private String getstatePR() {
		return dataModel.get(P_R_KEY,DEFAULT_P_R);
	}
	
	public void onP1RstateChange(final String val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(P1_R_KEY,val);
				
			}
		});
	}
	
	private String getstateP1R() {
		return dataModel.get(P1_R_KEY,DEFAULT_P1_R);
	}
	
	
	public void onP2RstateChange(final String val) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				dataModel.set(P2_R_KEY,val);
				
			}
		});
	}
	
	private String getstateP2R() {
		return dataModel.get(P2_R_KEY,DEFAULT_P2_R);
	}
	
	@Override
	public void openView() {
		view.updateError(this.ellipseState.getMessage(), this.ellipseState.isError());
		// view state initialization
		
		view.enableP(getstateP());
		view.enableP1(getstateP1());
		view.enableP2(getstateP2());
		
		view.LP.setText(getstatePR());
		view.LP1.setText(getstateP1R());
		view.LP2.setText(getstateP2R());
		
		view.enablePlusMinus(getstatePM());
		view.text.setText("");
		
		view.enableSet1(getstateSet1());
		view.enableSet2(getstateSet2());

		
		view.setLenLabel(getLen());
		view.setDiamLabel(getDiam());
		view.setPickLabel(getPick());

		view.setLenSlider(getLen());
		view.setDiamSlider(getDiam());
		
		getInstallation().checkInstal(getPick(), getDiam(), getLen(), poses);
		
		// check seril number
		
		if(0 !=getSeria().compareTo(seria)) {
			view.disable();
			
		}
		
		
		// Variable Factory Builder
		
		ExpressionBuilder expressionBuilder = apiProvider.getProgramAPI().getValueFactoryProvider().createExpressionBuilder();
		Expression initialValue = null;
		try {
			initialValue = expressionBuilder.append("0").build();
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		VariableFactory variableFactory = apiProvider.getProgramAPI().getVariableModel().getVariableFactory();
		try {
			GlobalVariable variable = variableFactory.createGlobalVariable("myVarTest", initialValue);
		} catch (VariableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public void closeView() {

	}

	@Override
	public String getTitle() {
		return "PickUpSet";
	}

	@Override
	public boolean isDefined() {

		return getstateDefine();
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		writer.writeChildren();
		System.out.println("Script from ProgramNode ");
		getInstallation().checkInstal(getPick(), getDiam(), getLen(), poses);
		
	}
	
	
	private PickInstalationNodeContribution getInstallation() {

		return apiProvider.getProgramAPI().getInstallationNode(PickInstalationNodeContribution.class);
	}

	// select Pick Up point using RobotPositionCallback
	public void selectPickUpPoint( final int l) {
		clearErrors();
		UserInterfaceAPI uiapi = apiProvider.getUserInterfaceAPI();
		view.text.setText("");
		if(getDiam()==0 || getLen()==0) {
			view.text.setText("Part Dimensions Required");
			return;
			
		}
		
		
		uiapi.getUserInteraction().getUserDefinedRobotPosition(new RobotPositionCallback2() {
			@Override
			public void onOk(PositionParameters positionParameters) {
				
						
				switch (l) {
				case 1:
					view.LP.setText("READY");
					onPRstateChange("READY");

					if(getPick()>1) {
						view.enableP1(true);
						onP1stateChange(true);
						view.enableP(false);
						onPstateChange(false);

					}else {
						System.out.println("Define true P!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						onDefinestateChange(true);
						view.enableP(false);
						onPstateChange(false);
					}
					System.out.println("Pick P form onOkRobot");
					break;
				case 2:
					view.LP1.setText("READY");
					onP1RstateChange("READY");

					if(getPick()>2) {
						view.enableP2(true);
						onP2stateChange(true);
						view.enableP1(false);
					    onP1stateChange(false);
					}else
					 {
						System.out.println("Define true P1!!!!!!!!!!!!!!!!!!!!!!!!!");
						onDefinestateChange(true);
						view.enableP1(false);
					    onP1stateChange(false);
					}
					System.out.println("Pick P1 form onOkRobot");
					break;
				case 3:
					view.LP2.setText("READY");
					onP2RstateChange("READY");
					text="P2";

					if(getPick()==3) {
						System.out.println("Define true P2!!!!!!!!!!!!!!!!!!!!!");
						onDefinestateChange(true);
						view.enableP2(false);
						onP2stateChange(false);
					}

					System.out.println("Pick P2 form onOkRobot");
					break;

				default:
					break;
				}
				
				 System.out.println("adjustWaypointsToPickPoint");
				 configurePoseOffset(positionParameters);

				
			}
		});
	}

	// unused 
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

	//calculate pose offset
	
	private void configurePoseOffset(PositionParameters positionParameters) {
	
		System.out.println("define pose fun ");
		
			int len = getLen();
			int dia = getDiam();
			int i =0;


		for(int j =0 ; j<6; j++)
		{
		
			double offsetX = 0;
			double offsetY = 0;
			double offsetZ = 0;
			
			switch (i) {
			case 0:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = -1.5*len;
				
				break;
			case 1:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = 0;
				break;
			case 2:
				 offsetX = 0;
				 offsetY = 0;
				 offsetZ = -0.05*len;
				 if(0.05*len <3) offsetZ = -3;
				break;
			case 3:
				 offsetX = 0;
				 offsetY = dia;
				 offsetZ = -0.05*len;
				 if(0.05*len <3) offsetZ = -3;
				break;
			case 4:
				 offsetX = 0;
				 offsetY = dia;
				 offsetZ = -0.55*len -3;
				break;
			case 5:
				 offsetX = 0;
				 offsetY = -1.5*dia;
				 offsetZ = -0.55*len-3;
				break;
			default:
				break;
			};

			createPoseConfig(positionParameters, offsetX, offsetY, offsetZ,i);
			i++;
			 System.out.println("define pose " +i);
		}
	}

	private void createPoseConfig(PositionParameters positionParameters, double xOffsetInMM,
													double yOffsetInMM, double zOffsetInMM, int i) {
		
		
		if(i==1 || i==7 || i==13) {
		Pose pose = createPoseUsingCenterPoseAndOffset(positionParameters.getPose(), xOffsetInMM, yOffsetInMM, zOffsetInMM,
				Length.Unit.MM);
		poses.add(pose);
		}else {
			Pose pose = createPoseUsingCenterPoseAndOffset1(positionParameters.getPose(), xOffsetInMM, yOffsetInMM, zOffsetInMM,
					Length.Unit.MM);
			// add pose to List
			poses.add(pose);	
		}
		
		System.out.println("add pose List");
		
		getInstallation().checkInstal(getPick(), getDiam(), getLen(), poses);
	
	}

	// create pose after Robot collback 
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
	private Pose createPoseUsingCenterPoseAndOffset1(Pose pose, double xOffset, double yOffset, double zOffset,
			Length.Unit unit) {

		double x =  xOffset;
		double y =  yOffset;
		double z =  zOffset;
		double rx = 0;
		double ry = 0;
		double rz = 0;
		ValueFactoryProvider valueFactoryProvider = apiProvider.getProgramAPI().getValueFactoryProvider();

		return valueFactoryProvider.getPoseFactory().createPose(x, y, z, rx, ry, rz, unit, Angle.Unit.RAD);
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
