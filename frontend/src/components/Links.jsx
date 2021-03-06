import {Collapse, Table, Button, Popover, Space, message, Modal, Form, Input, Select} from 'antd';
import React, {useState, useEffect} from 'react';
import {queryLinkApi, deleteLinkApi, updateLinkApi, createLinkApi} from "../request/api";
const { Option } = Select;
const {Panel} = Collapse;

export default function Links() {
    const [form] = Form.useForm();
    const [otherList, setOtherList] = useState([])
    const [perksList, setPerksList] = useState([])
    const [financeList, setFinanceList] = useState([])
    const [dataList, setDataList] = useState([])
    const [facilityList, setFacilityList] = useState([])
    const [trainingList, setTrainingList] = useState([])
    const [awardsList, setAwardsList] = useState([])
    const [editForm, setEditForm] = useState([]);
    const [dashboardsList, setDashboardsList] = useState([])
    const [visible, setVisible] = useState(false);
    const [visibleAdd, setVisibleAdd] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [confirmLoadingAdd, setConfirmLoadingAdd] = useState(false);
    const showModal = (data) => {
        setVisible(true);
        setEditForm(data);
    };
    const showModalAdd = () => {
        setVisibleAdd(true);
    };
    const onAdd = (values) => {
        createLinkApi({
            category: values.category,
            description: values.description,
            title: values.title,
            link: values.link
        }).then(res=>{
            if(res.status === 200) {
                message.success(res.msg);
                getLinksList();
                setConfirmLoadingAdd(true);
                form.resetFields();
                setTimeout(() => {
                    setVisibleAdd(false);
                    setConfirmLoadingAdd(false);
                }, 1000);
            }else{
                message.error(res.msg);
            }
        })
    }
    const onFinish = (values) => {
        updateLinkApi({

            id: editForm.key,
            category: values.category,
            description: values.description,
            title: values.title,
            link: values.link
        }).then(res=>{
            if(res.status === 200) {
                message.success(res.msg);
                getLinksList();
                setConfirmLoading(true);
                form.resetFields();
                setTimeout(() => {
                    setVisible(false);
                    setConfirmLoading(false);
                }, 1000);
            }else{
                message.error(res.msg);
            }
        })

    };

    const handleCancel = () => {
        setVisible(false);
    };
    const handleCancelAdd = () => {
        setVisibleAdd(false);
    };

    const getLinksList = () => {
        queryLinkApi({"category": "other"}).then(
            res => {
                let newArr = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr.map(item => {
                    item.key = item.id
                })
                setOtherList(newArr)
            }
        )
        queryLinkApi({"category": "perks"}).then(
            res => {
                let newArr = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr.map(item => {
                    item.key = item.id
                })
                setPerksList(newArr)
            }
        )

        queryLinkApi({"category": "finance"}).then(
            res => {
                let newArr1 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr1.map(item => {
                    item.key = item.id
                })
                setFinanceList(newArr1)
            }
        )

        queryLinkApi({"category": "data"}).then(
            res => {
                let newArr2 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr2.map(item => {
                    item.key = item.id
                })
                setDataList(newArr2)
            }
        )

        queryLinkApi({"category": "Dashboards/ IR visualization tools"}).then(
            res => {
                let newArr3 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr3.map(item => {
                    item.key = item.id
                })
                setDashboardsList(newArr3)
            }
        )

        queryLinkApi({"category": "Facility/Support"}).then(
            res => {
                let newArr4 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr4.map(item => {
                    item.key = item.id
                })
                setFacilityList(newArr4)
            }
        )

        queryLinkApi({"category": "Training"}).then(
            res => {
                let newArr5 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr5.map(item => {
                    item.key = item.id
                })
                setTrainingList(newArr5)
            }
        )

        queryLinkApi({"category": "Award"}).then(
            res => {
                let newArr6 = JSON.parse(JSON.stringify(res.data.weblinks))
                //the tables require key
                newArr6.map(item => {
                    item.key = item.id
                })
                setAwardsList(newArr6)
            }
        )
    }
    useEffect(() => {
        getLinksList()
    }, []);


    const onChange = (key) => {
        console.log(key);
    };
    const handleDelete = (id) => {
        deleteLinkApi({"id": id}).then(res => {
            if (res.status === 200) {
                message.success(res.msg)
                getLinksList()
            } else {
                message.success(res.msg)
            }
        })
    }
    const columns = [
        {
            title: 'Title',
            dataIndex: 'title',
            key: 'title',
            width: 20
        },
        {
            title: 'Link',
            dataIndex: 'link',
            key: 'link',
            width: 40,
            ellipsis: true,
            render: (_, {link}) => (
                <a target='_blank' href={("//" + link)}>
                    {link}
                </a>
            ),
        },
        {
            title: 'Description',
            dataIndex: 'description',
            key: 'description',
            width: 20,
            render: (_, {description}) => (
                <Popover content={description} trigger="hover">
                    <Button>Hover me</Button>
                </Popover>
            ),
        },
        {
            title: "Action",
            key: "action",
            width: 20,
            render: text => (
                <>
                    <Space size="middle">
                        <a onClick={() => showModal(text)}>Edit</a>
                        <a onClick={() => handleDelete(text.key)}>Delete</a>
                    </Space>
                    <Modal
                        title="Edit"
                        visible={visible}
                        confirmLoading={confirmLoading}
                        onCancel={handleCancel}
                        destroyOnClose={true}
                        footer={null}
                    >
                        <Form
                            name="basic"
                            labelCol={{
                                span: 6,
                            }}
                            wrapperCol={{
                                span: 16,
                            }}
                            initialValues={{
                                title: editForm.title,
                                link: editForm.link,
                                category: editForm.category,
                                description: editForm.description

                            }}
                            onFinish={onFinish}
                            autoComplete="off"
                        >
                            <Form.Item
                                label="title"
                                name="title"
                            >
                                <Input key={editForm.title} defaultValue={editForm.title}/>
                            </Form.Item>

                            <Form.Item
                                label="link"
                                name="link"
                            >
                                <Input key={editForm.link} defaultValue={editForm.link}/>
                            </Form.Item>
                            <Form.Item
                                label="description"
                                name="description"
                            >
                                <Input key={editForm.description} defaultValue={editForm.description}/>
                            </Form.Item>
                            <Form.Item label="category" name="category">
                                <Select defaultValue={editForm.category} style={{ width: 120 }}>
                                    <Option value="Finance">Finance</Option>
                                    <Option value="Data">Data</Option>
                                    <Option value="Dashboards/ IR visualization tools">Dashboards/ IR visualization tools</Option>
                                    <Option value="Facility/Support">Facility/Support</Option>
                                    <Option value="Training">Training</Option>
                                    <Option value="Award">Award</Option>
                                    <Option value="Perks">Perks</Option>
                                    <Option value="Other">Other</Option>
                                </Select>
                            </Form.Item>
                            <Form.Item
                                wrapperCol={{
                                    offset: 8,
                                    span: 16,
                                }}
                            >
                                <Button type="primary" htmlType="submit">
                                    Submit
                                </Button>
                            </Form.Item>
                        </Form>
                    </Modal>
                </>
            )
        }
    ];

    return (<div>
        <Button type="primary" onClick={showModalAdd}>Add Link</Button>
            <Modal
                title="Edit"
                visible={visibleAdd}
                confirmLoading={confirmLoadingAdd}
                onCancel={handleCancelAdd}
                destroyOnClose={true}
                footer={null}
            >
                <Form
                    name="basic"
                    labelCol={{
                        span: 6,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}

                    onFinish={onAdd}
                    autoComplete="off"
                >
                    <Form.Item
                        label="title"
                        name="title"
                    >
                        <Input key={"title"}/>
                    </Form.Item>

                    <Form.Item
                        label="link"
                        name="link"
                    >
                        <Input key={"link"} placeholder="Please input link without 'https://'"/>
                    </Form.Item>
                    <Form.Item
                        label="description"
                        name="description"
                    >
                        <Input key={"description"}/>
                    </Form.Item>
                    <Form.Item label="category" name="category">
                        <Select style={{ width: 120 }}>
                            <Option value="Finance">Finance</Option>
                            <Option value="Data">Data</Option>
                            <Option value="Dashboards/ IR visualization tools">Dashboards/ IR visualization tools</Option>
                            <Option value="Facility/Support">Facility/Support</Option>
                            <Option value="Training">Training</Option>
                            <Option value="Award">Award</Option>
                            <Option value="Perks">Perks</Option>
                            <Option value="Other">Other</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        <Collapse defaultActiveKey={['1']} onChange={onChange}>
            <Panel header="Perks" key="1">
                <p><Table dataSource={perksList} columns={columns} scroll={{y: 240}} pagination={false} size="small"/>
                </p>
            </Panel>
            <Panel header="Finance" key="2">
                <p><Table dataSource={financeList} columns={columns} scroll={{y: 240}} pagination={false} size="small"/>
                </p>
            </Panel>
            <Panel header="Data" key="3">
                <p><Table dataSource={dataList} columns={columns} scroll={{y: 240}} pagination={false} size="small"/>
                </p>
            </Panel>
            <Panel header="Dashboards/ IR visualization tools" key="4">
                <p><Table dataSource={dashboardsList} columns={columns} scroll={{y: 240}} pagination={false}
                          size="small"/></p>
            </Panel>
            <Panel header="Facility/Support" key="5">
                <p><Table dataSource={facilityList} columns={columns} scroll={{y: 240}} pagination={false}
                          size="small"/></p>
            </Panel>
            <Panel header="Training" key="6">
                <p><Table dataSource={trainingList} columns={columns} scroll={{y: 240}} pagination={false}
                          size="small"/></p>
            </Panel>
            <Panel header="Awards" key="7">
                <p><Table dataSource={awardsList} columns={columns} scroll={{y: 240}} pagination={false} size="small"/>
                </p>
            </Panel>
            <Panel header="Other" key="8">
                <p><Table dataSource={otherList} columns={columns} scroll={{y: 240}} pagination={false} size="small"/>
                </p>
            </Panel>
        </Collapse>
        </div>
    )
}