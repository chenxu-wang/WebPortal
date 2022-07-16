import React from 'react';
import './less/homepage.less'
import { Card, Col, Row} from 'antd';
import Links from "../components/Links";
const { Meta } = Card;
export default function Homepage() {
        return (
            <Row>
                <Col span={12} style={{padding: "10px"}}>
                    <Card
                        style={{ width: "100%"}}
                        cover={
                            <img
                                alt="example"
                                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                            />
                        }

                    >
                        <Meta
                            title="Card title"
                            description="This is the description"
                        />
                    </Card>
                </Col>
                <Col span={12} style={{padding: "10px"}}>
                    <Links />
                </Col>
            </Row>
        );
}
