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




