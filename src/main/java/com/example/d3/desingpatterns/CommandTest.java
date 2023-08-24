package com.example.d3.desingpatterns;

/**
 * @author wangchao
 * @Description:命令模式
 * @date 2023/8/24 14:57
 * @vVersion 1.0
 */
public class CommandTest {
    //创建命令接口
   static public interface Command {

        //执行动作(操作)
        public void execute();
        //撤销动作(操作)
        public void undo();
    }
    static class LightReceiver{
        public void on() {
            System.out.println(" 电灯打开了.. ");
        }

        public void off() {
            System.out.println(" 电灯关闭了.. ");
        }
    }
    static class LightOnCommand implements Command{
       private LightReceiver lightReceiver;

        public LightOnCommand(LightReceiver lightReceiver) {
            this.lightReceiver = lightReceiver;
        }

        @Override
        public void execute() {
            lightReceiver.on();
        }

        @Override
        public void undo() {
            lightReceiver.off();
        }
    }
    static class LightoffCommand implements Command{
        private LightReceiver lightReceiver;

        public LightoffCommand(LightReceiver lightReceiver) {
            this.lightReceiver = lightReceiver;
        }

        @Override
        public void execute() {
            lightReceiver.off();
        }

        @Override
        public void undo() {
            lightReceiver.on();
        }
    }

    /**
     * 空命令
     */
    static class NoCommand implements Command {

        @Override
        public void execute() {
            // TODO Auto-generated method stub

        }

        @Override
        public void undo() {
            // TODO Auto-generated method stub

        }
    }
    static class RemoteController{
        Command[] onCommands;
        Command[] offCommands;
        Command undoCommand;

        public RemoteController() {
            this.onCommands = new Command[5];
            this.offCommands = new Command[5];
            for (int i = 0; i < 5; i++) {
                onCommands[i]=new NoCommand();
                offCommands[i]=new NoCommand();
            }
        }
        public void setCommand(int no,Command onC,Command offC){
            onCommands[no]=onC;
            offCommands[no]=offC;
        }
        public void onButtonWasPushed(int no){
            onCommands[no].execute();
            undoCommand=onCommands[no];
        }
        public void offButtonWasPushed(int no){
            offCommands[no].execute();
            undoCommand=offCommands[no];
        }
        public void cancelButtonWasPushed(int no){
            undoCommand.undo();
        }
    }

    public static void main(String[] args) {
        //创建电灯的对象(接受者)
        LightReceiver lightReceiver = new LightReceiver();
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightoffCommand lightoffCommand = new LightoffCommand(lightReceiver);
        RemoteController remoteController = new RemoteController();
        remoteController.setCommand(0,lightOnCommand,lightoffCommand);
        System.out.println("按开关");
        remoteController.onButtonWasPushed(0);
    }
}
