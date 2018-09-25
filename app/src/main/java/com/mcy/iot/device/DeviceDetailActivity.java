package com.mcy.iot.device;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mcy.iot.BaseActivity;
import com.mcy.iot.R;
import com.mcy.iot.databinding.ActivityDeviceDetailBinding;
import com.mcy.iot.device.onenet.OneNETDevice;

public class DeviceDetailActivity extends BaseActivity {

    private ActivityDeviceDetailBinding binding;
    private OneNETDevice device;

    private ValueAnimator uploadAnimator;
    private ValueAnimator setTimeAnimator;

//    private DataStreams dataStreams;

    /**
     * 用于开关操作
     */
    private boolean isON = false;

    /**
     * 开关状态
     */
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_detail);
        setToolbar(binding.toolbar);

        device = (OneNETDevice) intent.getSerializableExtra("");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (device != null) {
            setTitle(device.getTitle());
            getData(device);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        simulateProgress();
    }

    private void simulateProgress() {
        binding.seekTime.setMaxProcess(100);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.seekTime.setCurProcess(progress);
//                binding.circleProgressBar.setProgress(progress);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }


            @Override
            public void onAnimationEnd(Animator animator) {
                simulate2Progress();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void simulate2Progress() {
        binding.seekTime.setMaxProcess(100);
        ValueAnimator animator = ValueAnimator.ofInt(100, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.seekTime.setCurProcess(progress);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                binding.seekTime.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * 设置数据动画效果
     */
    private void setTimeDataAnimator() {//总时间、剩余时间
        binding.seekTime.setVisibility(View.VISIBLE);
        binding.seekTime.setMaxProcess(100);
        setTimeAnimator = ValueAnimator.ofInt(0, 100);
        setTimeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.seekTime.setCurProcess(progress);
            }
        });
        setTimeAnimator.setDuration(1000);
        setTimeAnimator.start();
    }

    /**
     * 关闭设置时间的动画
     */
    private void CloseTimeDataAnimator() {
        binding.seekTime.setVisibility(View.GONE);
        if (setTimeAnimator != null && !setTimeAnimator.isRunning()) {
            setTimeAnimator.cancel();
        }
    }

    /**
     * 下达命令时，做个动画效果
     */
    private void ShowUploadAnimator() {
        binding.seekTime.setVisibility(View.VISIBLE);
        binding.seekTime.setMaxProcess(100);
        uploadAnimator = ValueAnimator.ofInt(0, 100);
        uploadAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.seekTime.setCurProcess(progress);
            }
        });
        uploadAnimator.setRepeatCount(ValueAnimator.INFINITE);
        uploadAnimator.setDuration(500);
        uploadAnimator.start();
    }

    private void CloseUploadAnimator() {
        binding.seekTime.setVisibility(View.GONE);
        if (uploadAnimator != null && !uploadAnimator.isRunning()) {
            uploadAnimator.cancel();
        }
    }

    /**
     * 获取单个设备数据
     */
    private void getData(OneNETDevice device) {
//        OneNetApi.querySingleDevice(device.getId(), new OneNetApiCallback() {
////            @Override
////            public void onSuccess(String response) {
////                setData(response);
////            }
////
////            @Override
////            public void onFailed(Exception e) {
////                Log.i("", "");
////            }
////        });

//        OneNetApi.queryMultiDataStreams(device.getId(), new OneNetApiCallback() {
//            @Override
//            public void onSuccess(String response) {
//                //TODO 需要做处理
//                Log.i("", "");
//                dataStreams = JSON.parseObject(response, DataStreams.class);
//                if (dataStreams != null && dataStreams.getErrno() == 0) {
//                    List<DataStream> list = dataStreams.getData();
//                    if (list.size() > 0) {
//                        for (DataStream dataStream : list) {
//                            if (dataStream.getId().equals("switch_status")) {
//                                isOpen = isON = dataStream.getCurrent_value() == 1;
//                                if (isON) {
//                                    binding.btSwitch.setBackgroundResource(R.drawable.bt_selector_circle_light_bg);
//                                } else {
//                                    binding.btSwitch.setBackgroundResource(R.drawable.bt_selector_circle_dark_bg);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//                Log.i("", "");
//            }
//        });
    }

    /**
     * 设置数据
     */
    private void setData(String data) {
        //TODO
    }


    public void onSwitch(View view) {
        if (isON) {
//            isON = false;
//            binding.btSwitch.setBackgroundResource(R.drawable.bt_selector_circle_dark_bg);
            sendCmdOFF(view);
        } else {
//            isON = true;
//            binding.btSwitch.setBackgroundResource(R.drawable.bt_selector_circle_light_bg);
            sendCmdON(view);
        }
    }

    public void sendCmdON(View view) {
        JSONObject object = new JSONObject();
        try {
            object.put("cmd", 1);
            object.put("switch", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = object.toString();
        sendCmdData(data);
    }

    public void sendCmdOFF(View view) {
        JSONObject object = new JSONObject();
        try {
            object.put("cmd", 1);
            object.put("switch", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = object.toString();
        sendCmdData(data);
    }

    /**
     * 延时打开关闭
     */
    public void setOnClickBySwitch(View view) {
        //TODO 弹框输入
        new MaterialDialog.Builder(this)
                .title("点击确定启用延时（" + (!isOpen ? "打开" : "关闭") + "）")
                .content("单位（秒）")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRangeRes(1, 3, R.color.yellow600)
                .input("", "10", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        dialog.dismiss();
                        int time = Integer.valueOf(input.toString());
                        JSONObject object = new JSONObject();
                        try {
                            object.put("cmd", 2);
                            object.put("switch", isOpen ? 0 : 1);
                            object.put("total_time", time);
                            object.put("remain_time", time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String data = object.toString();
                        sendCmdData(data);
                    }
                })
                .negativeText("取消")
                .negativeColor(Color.GRAY)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 定时控制
     */
    public void setOnClickByTiming(View view) {
        //TODO 待开发
        showHintSnackbar(binding.getRoot(), "码农正在努力开发中，请等待！");
    }

    /**
     * 循环定时
     */
    public void setOnClickByLoop(View view) {
        //TODO 待开发
        showHintSnackbar(binding.getRoot(), "码农正在努力开发中，请等待！");
    }

    /**
     * 统一在这里下达命令
     */
    public void sendCmdData(String data) {
//        ShowUploadAnimator();

       /* OneNetApi.sendCmdToDevice(device.getId(),
                true,
                10,//命令超时时间
                Command.CommandType.TYPE_CMD_REQ,
                data,
                new OneNetApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        //TODO 需要做处理
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("errno") == 0) {

                                showHintSnackbar(binding.getRoot(), "成功");

                                binding.btSwitch.setBackgroundResource(isOpen ? R.drawable.bt_selector_circle_dark_bg : R.drawable.bt_selector_circle_light_bg);
                                isOpen = !isOpen;
                                isON = !isON;

                                String data = object.optString("data");
                                JSONObject object2 = new JSONObject(data);
                                String cmd_uuid = object2.optString("cmd_uuid");
                                command = cmd_uuid;
                                queryCommand(null);
                            } else {
                                showHintSnackbar(binding.getRoot(), response);
                            }
//                            CloseUploadAnimator();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailed(Exception e) {
                        showHintSnackbar(binding.getRoot(), e.toString());
//                        CloseUploadAnimator();
                    }
                });*/
    }

    /**
     * 命令下达响应回调
     */
    private void queryCmdResponse(String cmdId) {
//        OneNetApi.queryCmdResponse(cmdId, new OneNetApiCallback() {
//            @Override
//            public void onSuccess(String response) {//{"errno":1,"error":"command created"}
////                showHintSnackbar(binding.getRoot(), response);
//            }
//
//            @Override
//            public void onFailed(Exception e) {
////                showHintSnackbar(binding.getRoot(), e.toString());
//            }
//        });
    }

    private String command = "";
    private int count = 0;

    /**
     * 查询命令状态
     */
    public void queryCommand(final View view) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* OneNetApi.queryCmdStatus(command, new OneNetApiCallback() {
            @Override
            public void onSuccess(String response) {
                String desc = "";
                try {
                    JSONObject object = new JSONObject(response);
                    String data = object.optString("data");
                    JSONObject object2 = new JSONObject(data);
                    int status = object2.optInt("status");

                    switch (status) {
                        case 0:
                            desc = "设备不在线";
                            break;
                        case 1:
                            desc = "命令已创建";
                            count++;
                            if (count >= 10) {//超过10s表示设备响应超时
                                count = 0;
                            } else {
                                queryCommand(null);
                            }
                            break;
                        case 2:
                            desc = "命令已发往设备";
                            break;
                        case 3:
                            desc = "命令发往设备失败";
                            break;
                        case 4:
                            desc = "设备正常响应";
                            break;
                        case 5:
                            desc = "命令执行超时";
                            break;
                        case 6:
                            desc = "设备响应消息过长 ";
                            break;
                    }
                    queryCmdResponse(command);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                showHintSnackbar(binding.getRoot(), desc);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });*/
    }
}
