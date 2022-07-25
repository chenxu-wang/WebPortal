import React, { useState} from 'react'
import { Upload, Button, message, Form, Spin } from 'antd';

const { Dragger } = Upload;

export default function Autogen() {
    const [fileList, setFileList] = useState([])
    const [pptUrl, setPptUrl] = useState()
    const [loading, setLoading] = useState(false);
    const uploadFile = () => {
        setLoading(true)
        for (const item of fileList) {
            const formData = new FormData()
            formData.append('file', item)
            const url = 'http://localhost:8081/Backend/importExcel';  //与后端上传接口一致
            fetch(url, {
                method: 'POST',
                body: formData,
                headers: {
                    'token': localStorage.getItem('token')
                }
            }).then((response) => {
                    setLoading(false)
                    response.blob().then(blob => {
                        const aLink = document.createElement('a');
                        document.body.appendChild(aLink);
                        aLink.style.display='none';
                        const objectUrl = window.URL.createObjectURL(blob);
                        console.log(blob)
                        setPptUrl(blob)
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

    const props = {
        name: 'file',
        multiple: false,
        maxCount: 1,
        accept: "application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-powerpoint, application/pdf, image/jpeg, image/png",
        action: 'http://localhost:8081/Backend/importExcel',
        headers: {
            authorization: 'authorization-text',
            'token': localStorage.getItem('token')
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
                fileList.splice(fileList.indexOf(info.file.originFileObj), 1)
            } else if (status === 'error') {
                message.error(`${info.file.name} file upload failed.`);
            }
        }
    };

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
            <Button type="primary" onClick={uploadFile}><Spin spinning={loading}/>Upload file</Button>
            <iframe
                src={`https://view.officeapps.live.com/op/embed.aspx?src=${pptUrl}`}
                width="100%"
                height="600px"
                frameBorder="0"
            >
            </iframe>
        </div>
    );
}
