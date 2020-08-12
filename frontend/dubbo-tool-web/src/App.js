import React from 'react';
import { Form, Input, Button,  Divider, Select, Space  } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import "antd/dist/antd.css";
import './App.css';
const { Option } = Select;
const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 10 },
};

const formItemLayoutWithOutLabel = {
    wrapperCol: { span: 10 },
};

class App extends React.Component {

    constructor(props){
    super(props)
    this.state = {
        items: [],
        url: '',
        group: '',
        impl: '',
        method: '',
        groupData: [],
        implData: [],
        methodData: [],
        searchValue: "",
        params:[],
		result:''
    }
    }

    //组件将要挂载时候触发的生命周期函数
    componentWillMount(){
    //
      var req = {};
      fetch('/zk/listRegistry', {
          method: 'post',
          headers: {
              "Content-type": "application/json"
          },
          body: JSON.stringify(req)
      })
          .then(res => res.json())
          .then(resp => {
              const items = resp.data;
              this.setState({
                  items: items,
                  name: '',
              });
          });
    }
    //组件挂载完成时候触发的生命周期函数
    componentDidMount(){

    }

    onUrlChange = event => {
    this.setState({
      url: event.target.value,
    });
    }

    handleSearch  = (value) => {
        const { items } = this.state;
        if (value) {
            var itemNews = [];
            var reg = new RegExp(value);
            for (let i = 0; i < items.length; i++) {
                var url = items[i].url;
                if (url.test(reg)) {
                   itemNews.push(items[i]);
                }
            }

            this.setState({
                items: itemNews,
                url: value
            });
        } else {
            this.setState({ data: [] });
        }
    }

    handleZKChange = (value) => {
        var req = {};
        req.name = value;
        req.url = value;
        fetch('/dubbo/listGroup', {
            method: 'post',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(req)
        })
            .then(res => res.json())
            .then(resp => {
                const items = resp.data;
                this.setState({
                    groupData: items,
                    url: value
                });
            });
    }

    handleImplChange = (value) => {
        const { group, url} = this.state;
        var req = {};
        req.name = url;
        req.url = url;
        req.group = group;
        req.serviceUrl = value;
        fetch('/dubbo/listMethod', {
            method: 'post',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(req)
        })
            .then(res => res.json())
            .then(resp => {
                const items = resp.data;
                this.setState({
                    methodData: items,
                    impl: value
                });
            });
    }

    handleMethodChange = (value) => {
        this.setState({
            method: value
        });
    }

    handleGroupChange = (value) => {
        const { url } = this.state;
        var req = {};
        req.name = url;
        req.url = url;
        req.group = value;
        fetch('/dubbo/listInterface', {
            method: 'post',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(req)
        })
            .then(res => res.json())
            .then(resp => {
                const items = resp.data;
                this.setState({
                    implData: items,
                    group: value
                });
            });
    }

      onFinish = (values) => {
          //const { items } = this.state;
		  
          let param = {
              name:  this.state.url,
              url: this.state.url,
              group:  this.state.group,
              serviceUrl:  this.state.impl,
              method:  this.state.method,
              params: values.params
          };
		  if (param.params) {
			for (var i = 0; i < param.params.length; i++) {
				if (param.params[i].paramContext.indexOf("{")>-1) {
					param.params[i].paramContext = JSON.parse(param.params[i].paramContext);
				}
			  }  
		  }
		  
		  console.log(param);

		  fetch('/dubbo/doAchieve', {
            method: 'post',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(param)
        })
            .then(res => res.json())
            .then(resp => {
                const items = JSON.stringify(resp.data, null, "\t");
                this.setState({
                    result:items
                });
            });
      }

      collectForm = (e, props) =>  {
          //const { param } = this.state;
          let params = [];
          this.setState({
              params
          })
      }

      addItem = (event) => {
          event.preventDefault();
        const { items } = this.state;
        const req = {
            name: this.state.url,
            url: this.state.url
        }
        // 连接ZK /zk/addRegistry
          fetch('/zk/addRegistry', {
              method: 'post',
              headers: {
                  "Content-type": "application/json"
              },
              body: JSON.stringify(req)
          })
              .then(res => res.json())
              .then(resp => {
                  const newItem = resp.data;
                  this.setState({
                      items: [...items, newItem],
                      name: '',
                  });
              });

      }

      render () {
         const { items, url, groupData, implData, methodData, group, impl, method, result} = this.state;

        return (<div className="App">
           <h3 className ="TitleM">Dubbo Test 1.0</h3>
           <Form {...layout} onFinish={this.onFinish}>
            <Form.Item label="ZK地址" name="zkaddress" rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请输入ZK地址",
                                                   },
                                               ]}>
            <Select
                showSearch
                value={url}
                defaultActiveFirstOption={false}
                showArrow={false}
                optionFilterProp = "children"
                //filterOption={false}
                filterOption = {(input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                //onSearch={this.handleSearch}
                //notFoundContent={null}
                //style={{ width: 240 }}
                placeholder="请输入ZK地址"
                dropdownRender={menu => (
                  <div>
                    {menu}
                    <Divider style={{ margin: '4px 0' }} />
                    <div style={{ display: 'flex', flexWrap: 'nowrap', padding: 8 }}>
                      <Input style={{ flex: 'auto' }} value={url} onChange={this.onUrlChange} placeholder="ip:port形式"/>
                      <a
                        style={{ flex: 'none', padding: '8px', display: 'block', cursor: 'pointer' }}
                        onClick={this.addItem}
                        href="/#"
                      >
                        <PlusOutlined />
                      </a>
                    </div>
                  </div>
                )}

                onChange={this.handleZKChange}
          >
            {items.map(item => (
              <Option key={item.name}>{item.url}</Option>
            ))}
          </Select>
            </Form.Item>
            <Form.Item label="Group分组" rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请选择分组",
                                                   },
                                               ]}>
                <Select
                    showSearch
                    value = {group}
                    placeholder = "请输入Group"
                    optionFilterProp = "children"
                    //defaultValue={groupData[0]}
                    //style={{ width: 120 }}
                    onChange={this.handleGroupChange}
                    filterOption = {(input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                >
                    {groupData.map(group => (
                        <Option key={group} value = {group}>{group}</Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item label="接口名称" rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请选择接口",
                                                   },
                                               ]}>
              <Select
                  showSearch
                  value = {impl}
                  placeholder = "请输入Impl"
                  optionFilterProp = "children"
                  filterOption = {(input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                  //defaultValue={implData[0]}
                  //style={{ width: 120 }}
                  onChange={this.handleImplChange}
              >
                  {implData.map(impl => (
                      <Option key={impl} value= {impl}>{impl}</Option>
                  ))}
              </Select>
            </Form.Item>
               <Form.Item label="方法" rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请选择方法",
                                                   },
                                               ]}>
                   <Select
                       showSearch
                       value = {method}
                       placeholder = "请输入Method"
                       optionFilterProp = "children"
                       filterOption = {(input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                       //defaultValue={methodData[0]}
                       //style={{ width: 120 }}
                       onChange={this.handleMethodChange}
                   >
                       {methodData.map(method => (
                           <Option key={method} value={method}>{method}</Option>
                       ))}
                   </Select>
               </Form.Item>
               <Form.List name="params" >
                   {(fields, { add, remove }) => {
                       return (
                           <div>
                               {fields.map((field, index) => (
                                   <Form.Item key={field.key} label="入参">
                                           <Form.Item
                                               {...field}
											   name={[field.name, 'paramType']}
												key={[field.key, 'paramType']}
                                               rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请输入参数类型，或删除此输入框",
                                                   },
                                               ]}
                                               noStyle
                                           >
                                               <Input placeholder="参数类型e.g. java.lang.String" style={{ width: '90%' }}/>
                                           </Form.Item>
										
                                           <Form.Item
                                               {...field}
											   name={[field.name, 'paramContext']}
												key={[field.key, 'paramContext']}
                                               rules={[
                                                   {
                                                       required: true,
                                                       whitespace: false,
                                                       message: "请输入参数内容，或删除此输入框",
                                                   },
                                               ]}
                                               noStyle
                                           >
                                               <Input.TextArea placeholder="参数内容，json格式串{}" 
											   style={{ width: '90%' }} autoSize={{ minRows: 10}}/>
                                           </Form.Item>
                                           {fields.length > 0 ? (
                                               <MinusCircleOutlined
                                                   className="dynamic-delete-button"
                                                   style={{ margin: '0 8px' }}
                                                   onClick={() => {
                                                       remove(field.name);
                                                   }}
                                               />
                                           ) : null}
									</Form.Item>
                               ))}
                               <Form.Item label="增加入参">
                                   <Button
                                       type="dashed"
                                       onClick={() => {
                                           add();
                                       }}
                                       style={{ width: '60%' }}
                                   >
                                       <PlusOutlined /> Add field
                                   </Button>
                               </Form.Item>
                           </div>
                       );
                   }}
               </Form.List>
             <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
              <Button type="primary" htmlType="submit">
                发送
              </Button>
            </Form.Item>
            <Form.Item label="响应结果">
             <Input.TextArea
			 value={result}
              placeholder="以json格式字符串展示"
              autoSize={{ minRows: 10 }}
            />
            </Form.Item>
            </Form>
       </div>);
      }
}

export default App;
