import React, { useState} from 'react'
import { Upload, Button, message, Form, Spin, Input } from 'antd';
import './less/autogen.less'
import pptEx from "./img.png";

const { Dragger } = Upload;

export default function Autogen() {
    const [fileList, setFileList] = useState([])
    const [loading, setLoading] = useState(false);
    const [compactLoading, setCompactLoading] = useState(false);

    const [titleSize, setTitleSize] = useState('20')
    const [axisNumSize, setAxisNumSize] = useState('10')
    const [axisTitleSize, setAxisTitleSize] = useState('12')
    const [legendFontSize, setLegendFontSize] = useState('10')
    const  [chartWidth, setChartWidth] = useState('22')
    const [chartHeight, setChartHeight] = useState('12')
    const  [chartX, setChartX] = useState('1.8')
    const [chartY, setChartY] = useState('4.5')

    const uploadFile = () => {
        if(fileList.length === 0){
            message.error("Please select the file for generating PowerPoint")
            return
        }

        setLoading(true)
        let i = 0
        for (const item of fileList) {
            i = i + 1
            if (i === fileList.length) {
                const formData = new FormData()
                formData.append('file', item)
                formData.append('titleSize', titleSize)
                formData.append('axisNumSize', axisNumSize)
                formData.append('axisTitleSize', axisTitleSize)
                formData.append('legendFontSize', legendFontSize)
                formData.append('chartWidth', chartWidth)
                formData.append('chartHeight', chartHeight)
                formData.append('chartX', chartX)
                formData.append('chartY', chartY)

                const url = 'http://localhost:8081/Backend/excel/downloadStandard';
                fetch(url, {
                    method: 'POST',

                    body: formData,
                    headers: {
                        'token': localStorage.getItem('token')
                    }
                }).then((response) => {
                        message.success("PowerPoint generated successful!")
                        setLoading(false)
                        response.blob().then(blob => {
                            const aLink = document.createElement('a');
                            document.body.appendChild(aLink);
                            aLink.style.display = 'none';
                            const objectUrl = window.URL.createObjectURL(blob);
                            console.log(objectUrl)
                            aLink.href = objectUrl;
                            aLink.download = "generatedPPT.pptx";
                            aLink.click();
                            document.body.removeChild(aLink);
                        });
                    }
                ).catch((error) => {
                    console.log('error', error);
                });
            }
        }
    }
    const downloadCompact = () => {
        if(fileList.length === 0){
            message.error("Please select the file for generating PowerPoint")
            return
        }

        setCompactLoading(true)
        let i = 0
        for (const item of fileList) {
            i = i + 1
            if (i === fileList.length) {
                const formData = new FormData()
                formData.append('file', item)
                formData.append('titleSize', titleSize)
                formData.append('axisNumSize', axisNumSize)
                formData.append('axisTitleSize', axisTitleSize)
                formData.append('legendFontSize', legendFontSize)
                formData.append('chartWidth', chartWidth)
                formData.append('chartHeight', chartHeight)
                formData.append('chartX', chartX)
                formData.append('chartY', chartY)

                const url = 'http://localhost:8081/Backend/excel/downloadCompact';
                fetch(url, {
                    method: 'POST',

                    body: formData,
                    headers: {
                        'token': localStorage.getItem('token')
                    }
                }).then((response) => {
                        message.success("PowerPoint generated successful!")
                        setCompactLoading(false)
                        response.blob().then(blob => {
                            const aLink = document.createElement('a');
                            document.body.appendChild(aLink);
                            aLink.style.display = 'none';
                            const objectUrl = window.URL.createObjectURL(blob);
                            console.log(objectUrl)
                            aLink.href = objectUrl;
                            aLink.download = "generatedPPT.pptx";
                            aLink.click();
                            document.body.removeChild(aLink);
                        });
                    }
                ).catch((error) => {
                    console.log('error', error);
                });
            }
        }
    }

    const props = {
        name: 'file',
        multiple: false,
        maxCount: 1,
        accept: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        // action: 'http://localhost:8081/Backend',

        headers: {
            authorization: 'authorization-text',
            // 'token': localStorage.getItem('token')
        },
        beforeUpload: (file) => {

            setFileList([...fileList, file]);
            return false;
        },
        onChange(info) {
            const { status } = info.file;
            if (status !== 'uploading') {
                console.log(info.file, info.file.fileList)
            }
            if (status === 'done') {
                message.success(`${info.file.name} file uploaded successfully.`);
                setFileList([info.file.originFileObj])
            } else if (status === 'removed') {
                setFileList(fileList.splice(fileList.indexOf(info.file.originFileObj), 1))
            } else if (status === 'error') {
                message.error(`${info.file.name} file upload failed.`);
            }
        }
    };
    const titleChange = (event) =>{
        setTitleSize(event.target.value)
    }
    const axisNumberChange = (event) =>{
        setAxisNumSize(event.target.value)
    }
    const axisTitleChange = (event) =>{
        setAxisTitleSize(event.target.value)
    }
    const legendFontSizeChange = (event) =>{
        setLegendFontSize(event.target.value)
    }
    const chartWidthChange = (event) =>{
        setChartWidth(event.target.value)
    }
    const chartHeightSizeChange = (event) =>{
        setChartHeight(event.target.value)
    }
    const chartXChange = (event) =>{
        setChartX(event.target.value)
    }
    const chartYChange = (event) =>{
        setChartY(event.target.value)
    }
    return (
        <div>
            <Form encType="multipart/form-data" style={{ marginTop:20}}>
                <Dragger {...props} >
                    <Button type="primary">Select file</Button>
                    <p style={{ marginTop:10}}>
                        Please select a xlsx file to upload.
                    </p>
                </Dragger>
            </Form>
            <div className="powerPointExample">
                <Input className="titleSize" placeholder="Input title size" onChange={titleChange} style={{width:"150px"}}/>
                <Input className="axisNum" placeholder="Input axis number size" onChange={axisNumberChange}/>
                <Input className="axisTitle" placeholder="Input axis title size" onChange={axisTitleChange}/>
                <Input className="legendFont"  placeholder="Input legend size" onChange={legendFontSizeChange}/>
                <Input className="chartWidth" placeholder="Input chart width" onChange={chartWidthChange}/>
                <Input className="chartHeight"  placeholder="Input chart height" onChange={chartHeightSizeChange}/>
                <Input className="chartX" placeholder="Input chart X coordinate" onChange={chartXChange}/>
                <Input className="chartY"  placeholder="Input chart Y coordinate" onChange={chartYChange}/>
            </div>
            {/*<div>*/}
            {/*    <label>PowerPoint Example: </label> <img src={pptEx} width="563.2px" height="422.5px" />*/}
            {/*</div>*/}
            <Button type="primary" onClick={uploadFile}><Spin spinning={loading}/>Download standard PowerPoint</Button>
            <Button className="downloadCompact" type="primary" onClick={downloadCompact}><Spin spinning={compactLoading}/>Download compact PowerPoint</Button>
        </div>
    );
}
