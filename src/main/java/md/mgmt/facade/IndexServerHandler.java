/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package md.mgmt.facade;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import md.mgmt.facade.mapper.CommandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
@Component
public class IndexServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(IndexServerHandler.class);

    @Autowired
    private CommandMapper commandMapper = new CommandMapper();

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        context.getBean("commandMapper");
    }*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info(String.valueOf(msg));
        String respStr = commandMapper.selectService((String) msg);
        logger.info("resp:" + respStr);
        ChannelFuture f = ctx.writeAndFlush(respStr);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
