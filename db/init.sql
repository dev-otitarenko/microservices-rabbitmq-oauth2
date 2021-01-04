CREATE DATABASE authdb
GO

USE authdb


CREATE TABLE [dbo].[oauth_access_token](
    [token_id] [varchar](256) NOT NULL,
    [token] [varbinary](max) NULL,
    [authentication_id] [varchar](256) NULL,
    [user_name] [varchar](256) NULL,
    [client_id] [varchar](256) NULL,
    [authentication] [varbinary](max) NULL,
    [refresh_token] [varchar](256) NULL
    ) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD PRIMARY KEY CLUSTERED
    (
    [token_id] ASC
    )
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD  DEFAULT (NULL) FOR [token_id]
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD  DEFAULT (NULL) FOR [authentication_id]
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD  DEFAULT (NULL) FOR [user_name]
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD  DEFAULT (NULL) FOR [client_id]
    GO
ALTER TABLE [dbo].[oauth_access_token] ADD  DEFAULT (NULL) FOR [refresh_token]
    GO

-- oauth_client_details
CREATE TABLE [dbo].[oauth_client_details](
    [client_id] [varchar](255) NOT NULL,
    [resource_ids] [varchar](255) NULL,
    [client_secret] [varchar](255) NULL,
    [scope] [varchar](255) NULL,
    [authorized_grant_types] [varchar](255) NULL,
    [web_server_redirect_uri] [varchar](255) NULL,
    [authorities] [varchar](255) NULL,
    [access_token_validity] [int] NULL,
    [refresh_token_validity] [int] NULL,
    [additional_information] [varchar](4096) NULL,
    [autoapprove] [varchar](255) NULL
    ) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD PRIMARY KEY CLUSTERED
    (
    [client_id] ASC
    )
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [resource_ids]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [client_secret]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [scope]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [authorized_grant_types]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [web_server_redirect_uri]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [authorities]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [access_token_validity]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [refresh_token_validity]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [additional_information]
    GO
ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [autoapprove]
    GO

-- oauth_refresh_token
CREATE TABLE [dbo].[oauth_refresh_token](
    [token_id] [varchar](256) NOT NULL,
    [token] [varbinary](max) NULL,
    [authentication] [varbinary](max) NULL
    ) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
    GO
ALTER TABLE [dbo].[oauth_refresh_token] ADD  DEFAULT (NULL) FOR [token_id]
    GO

ALTER TABLE [dbo].[oauth_refresh_token] ADD PRIMARY KEY CLUSTERED
    (
    [token_id] ASC
    )
    GO

-- insert data
delete from oauth_client_details

insert into oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, additional_information)
values ('adminapp', '{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'read,write,admin', 'authorization_code,password,refresh_token,implicit', 9000, 60000, '{}')

-- LOG_CONNECTS
CREATE TABLE [dbo].[logs_connects](
    [id] [varchar](32) NOT NULL,
    [oper_date] [datetime] NOT NULL,
    [iduser] [varchar](32) NOT NULL,
    [username] [varchar](96) NOT NULL,
    [ip_address] [varchar](20) NOT NULL,
    [country_code] [varchar](3) NOT NULL,
    [country_name] [varchar](96) NOT NULL,
    [city_name] [varchar](48) NULL,
    [device_details] [varchar](512) NULL
    ) ON [PRIMARY]
GO
ALTER TABLE [dbo].[logs_connects] ADD  CONSTRAINT [pk_logs_connects] PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )
GO
CREATE NONCLUSTERED INDEX [i_logs_connects_iduser] ON [dbo].[logs_connects]
(
	[iduser] ASC
)
GO

-- LOG_PRIVATE_EVENTS
CREATE TABLE [dbo].[logs_events_private](
    [id] [varchar](32) NOT NULL,
    [oper_date] [datetime] NOT NULL,
    [mode] [int] NOT NULL,
    [name] [varchar](256) NOT NULL,
    [description] [varchar](5126) NULL,
    [iduser] [varchar](32) NOT NULL,
    [username] [varchar](96) NOT NULL
    ) ON [PRIMARY]
GO
ALTER TABLE [dbo].[logs_events_private] ADD  CONSTRAINT [pk_logs_events_private] PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )
GO
CREATE NONCLUSTERED INDEX [i_logs_events_private_iduser] ON [dbo].[logs_events_private]
(
	[iduser] ASC
)
GO

-- LOG_PUBLIC_EVENTS
CREATE TABLE [dbo].[logs_events_public](
    [id] [varchar](32) NOT NULL,
    [oper_date] [datetime] NOT NULL,
    [mode] [int] NOT NULL,
    [name] [varchar](256) NOT NULL,
    [description] [varchar](5126) NULL,
    [ip_address] [varchar](20) NOT NULL,
    [country_code] [varchar](3) NOT NULL,
    [country_name] [varchar](96) NOT NULL,
    [city_name] [varchar](48) NULL
    ) ON [PRIMARY]
    GO
    SET ANSI_PADDING ON
    GO
ALTER TABLE [dbo].[logs_events_public] ADD  CONSTRAINT [logs_events_public] PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )
GO

-- USER_MESSAGES
CREATE TABLE [dbo].[user_messages](
    [CODE] [varchar](32) NOT NULL,
    [ID_USER] [varchar](32) NOT NULL,
    [OPER_DATE] [datetime] NOT NULL,
    [STATE] [smallint] NOT NULL,
    [TITLE] [varchar](256) NOT NULL,
    [EXECUTION_TIME] [float] NOT NULL,
    [MESSAGE] [varchar](1024) NULL,
    [ID_DOWNLOAD] [varchar](32) NULL,
    [FNAME] [varchar](96) NULL
    ) ON [PRIMARY]
GO
ALTER TABLE [dbo].[user_messages] ADD  CONSTRAINT [pk_user_messages] PRIMARY KEY CLUSTERED
    (
    [CODE] ASC
)
GO
CREATE NONCLUSTERED INDEX [i_user_messages] ON [dbo].[user_messages]
(
	[ID_USER] ASC
)
GO
ALTER TABLE [dbo].[user_messages] ADD  DEFAULT ((0)) FOR [STATE]
GO

-- USERS
CREATE TABLE [dbo].[users](
    [ID] [varchar](32) NOT NULL,
    [USERNAME] [varchar](96) NULL,
    [ID_ORG] [varchar](32) NULL,
    [FULL_NAME] [varchar](512) NOT NULL,
    [D_BEGIN] [date] NOT NULL,
    [ID_ORG_MAIN] [varchar](32) NULL,
    [PSW] [varchar](128) NULL,
    [EMAIL] [varchar](256) NULL,
    [PHONE] [varchar](256) NULL,
    [OPER_DATE] [datetime2](7) NULL,
    [OPER_IDWORKER] [varchar](32) NULL,
    [LAST_LOGON] [datetime2](7) NULL,
    [ENABLED] [bit] NOT NULL,
    [account_expired] [bit] NOT NULL,
    [credentials_expired] [bit] NOT NULL,
    [account_locked] [bit] NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [PK_USERS] PRIMARY KEY CLUSTERED
    (
    [ID] ASC
)
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [D_BEGIN]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (sysdatetime()) FOR [OPER_DATE]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((1)) FOR [ENABLED]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [account_expired]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [credentials_expired]
    GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [account_locked]
    GO

-- ORGS
CREATE TABLE [dbo].[orgs](
    [ID] [varchar](32) NOT NULL,
    [NAME] [varchar](1024) NULL,
    [ID_PAR] [varchar](32) NULL,
    [OPER_DATE] [datetime2](7) NULL,
    [OPER_IDWORKER] [varchar](32) NULL,
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[orgs] ADD  CONSTRAINT [PK_ORGS] PRIMARY KEY CLUSTERED
    (
    [ID] ASC
    )
    GO
CREATE NONCLUSTERED INDEX [i_orgs_idpar] ON [dbo].[orgs]
(
	[ID_PAR] ASC
)
GO
ALTER TABLE [dbo].[orgs] ADD  DEFAULT (sysdatetime()) FOR [OPER_DATE]
    GO

-- ROLES
CREATE TABLE [dbo].[roles](
    [ID] [int] NOT NULL,
    [NAME] [nvarchar](128) NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[roles] ADD PRIMARY KEY CLUSTERED
    (
    [ID] ASC
    )
    GO
ALTER TABLE [dbo].[roles] ADD UNIQUE NONCLUSTERED
    (
    [NAME] ASC
    )
    GO

-- USER_ROLES
CREATE TABLE [dbo].[user_roles](
    [ID_USER] [varchar](32) NOT NULL,
    [ID_ROLE] [int] NOT NULL,
    [OPER_DATE] [datetime2](7) NULL,
    [OPER_IDWORKER] [varchar](32) NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[user_roles] ADD PRIMARY KEY CLUSTERED
    (
    [ID_USER] ASC,
    [ID_ROLE] ASC
    )
    GO
CREATE NONCLUSTERED INDEX [i_user_roles_idrole] ON [dbo].[user_roles]
(
	[ID_ROLE] ASC
)
GO

-- PERMISSIONS
CREATE TABLE [dbo].[permissions](
    [ID] [int] NOT NULL,
    [NAME] [nvarchar](60) NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[permissions] ADD PRIMARY KEY CLUSTERED
    (
    [ID] ASC
    )
    GO
ALTER TABLE [dbo].[permissions] ADD UNIQUE NONCLUSTERED
    (
    [NAME] ASC
    )
    GO

-- PERMISSION_ROLES
CREATE TABLE [dbo].[permission_roles](
    [ID_ROLE] [int] NOT NULL,
    [ID_PERMISSION] [int] NOT NULL
) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[permission_roles] ADD PRIMARY KEY CLUSTERED
    (
    [ID_ROLE] ASC,
    [ID_PERMISSION] ASC
    )
    GO
ALTER TABLE [dbo].[permission_roles]  WITH CHECK ADD  CONSTRAINT [permission_role_fk1] FOREIGN KEY([ID_PERMISSION])
    REFERENCES [dbo].[permissions] ([ID])
    ON UPDATE CASCADE
       ON DELETE CASCADE
GO
ALTER TABLE [dbo].[permission_roles] CHECK CONSTRAINT [permission_role_fk1]
    GO
ALTER TABLE [dbo].[permission_roles]  WITH CHECK ADD  CONSTRAINT [permission_role_fk2] FOREIGN KEY([ID_ROLE])
    REFERENCES [dbo].[roles] ([ID])
    ON UPDATE CASCADE
       ON DELETE CASCADE
GO
ALTER TABLE [dbo].[permission_roles] CHECK CONSTRAINT [permission_role_fk2]
    GO
ALTER TABLE [dbo].[user_roles] ADD  DEFAULT (sysdatetime()) FOR [OPER_DATE]
    GO
ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk_user_roles_1] FOREIGN KEY([ID_ROLE])
    REFERENCES [dbo].[roles] ([ID])
    ON UPDATE CASCADE
       ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [fk_user_roles_1]
    GO
ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk_user_roles_2] FOREIGN KEY([ID_USER])
    REFERENCES [dbo].[users] ([ID])
    ON UPDATE CASCADE
       ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [fk_user_roles_2]
    GO



