<!--

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->

# 代码提交指南

## 贡献途径

IoTDB诚邀广大开发者参与开源项目构建

您可以查看[issues](https://issues.apache.org/jira/projects/IOTDB/issues)并参与解决，或者做其他改善。

提交pr，通过Travis-CI测试和Sonar代码质量检测后，至少有一位以上Committer同意且代码无冲突，就可以合并了

## PR指南

在Github上面可以很方便地提交 [Pull Request (PR)](https://help.github.com/articles/about-pull-requests/)，下面将以本网站项目[apache/incubator-iotdb](https://github.com/apache/incubator-iotdb) 为例（如果是其他项目，请替换项目名incubator-iotdb）

### Fork仓库

进入 apache/incubator-iotdb 的 [github 页面](https://github.com/apache/incubator-iotdb) ，点击右上角按钮 `Fork` 进行 Fork

![](https://user-images.githubusercontent.com/37333508/79351839-bd288900-7f6b-11ea-8d12-feb18c35adad.png)

### 配置git和提交修改

- 将代码克隆到本地：

```
git clone https://github.com/<your_github_name>/incubator-iotdb.git
```

**请将 <your_github_name> 替换为您的github名字**

clone完成后，origin会默认指向github上的远程fork地址。

- 将 apache/incubator-iotdb 添加为本地仓库的远程分支 upstream：

```
cd  incubator-iotdb
git remote add upstream https://github.com/apache/incubator-iotdb.git
```

- 检查远程仓库设置：

```
git remote -v
origin https://github.com/<your_github_name>/incubator-iotdb.git (fetch)
origin    https://github.com/<your_github_name>/incubator-iotdb.git(push)
upstream  https://github.com/apache/incubator-iotdb.git (fetch)
upstream  https://github.com/apache/incubator-iotdb.git (push)
```

- 新建分支以便在分支上做修改：（假设新建的分支名为fix）

```
git checkout -b fix
```

创建完成后可进行代码更改。

- 提交代码到远程分支：（此处以fix分支为例）

```
git commit -a -m "<you_commit_message>"
git push origin fix
```

更多 git 使用方法请访问：[git 使用](https://www.atlassian.com/git/tutorials/setting-up-a-repository)，这里不赘述。

### 创建PR

在浏览器切换到自己的 github 仓库页面，切换分支到提交的分支 <your_branch_name> ，依次点击 `New pull request` 和 `Create pull request` 按钮进行创建，如果您解决的是[issues](https://issues.apache.org/jira/projects/IOTDB/issues)，需要在开头加上[IOTDB-xxx]，如下图所示：

![](https://user-images.githubusercontent.com/37333508/79414865-5f815480-7fde-11ea-800c-47c7dbad7648.png)

至此，您的PR创建完成，更多关于 PR 请阅读 [collaborating-with-issues-and-pull-requests](https://help.github.com/categories/collaborating-with-issues-and-pull-requests/)

### 冲突解决

提交PR时的代码冲突一般是由于多人编辑同一个文件引起的，解决冲突主要通过以下步骤即可：

1：切换至主分支

```
git checkout master
```

2：同步远端主分支至本地

```
git pull upstream master
```

3：切换回刚才的分支（假设分支名为fix）

```
git checkout fix
```

4：进行rebase

```
git rebase -i master
```

此时会弹出修改记录的文件，一般直接保存即可。然后会提示哪些文件出现了冲突，此时可打开冲突文件对冲突部分进行修改，将提示的所有冲突文件的冲突都解决后，执行

```
git add .
git rebase --continue
```

依此往复，直至屏幕出现类似 *rebase successful* 字样即可，此时您可以进行往提交PR的分支进行更新：

```
git push -f origin fix
```



这个指导文档修改于[Apache ServiceComb](http://servicecomb.apache.org/developers/submit-codes/)